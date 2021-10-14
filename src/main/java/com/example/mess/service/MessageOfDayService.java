package com.example.mess.service;

import com.example.mess.domain.MessageOfDay;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class MessageOfDayService {

    public static final String COLLECTION_NAME_MESSAGES = "messages";
    public static final String COLLECTION_NAME_PREV_MESSAGES = "prev_messages";

    List<MessageOfDay> listOfMessages = new ArrayList<MessageOfDay>();
    List<MessageOfDay> listPreviousMessages = new ArrayList<MessageOfDay>();

    public String saveMessage(MessageOfDay messageOfDay, String collectionName) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        messageOfDay.setId(UUID.randomUUID().toString());

        ApiFuture<WriteResult> collectionApiFuture = dbFirestore
                .collection(collectionName)
                .document(messageOfDay.getId())
                .set(messageOfDay);

        return  collectionApiFuture.get().getUpdateTime().toString();
    }

    public String updateMessage(MessageOfDay messageOfDay, String collectionName) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        messageOfDay.setDisplayDate(LocalDateTime.now().toString());

        ApiFuture<WriteResult> collectionApiFuture = dbFirestore
                .collection(collectionName)
                .document(messageOfDay.getId())
                .set(messageOfDay);

        return  collectionApiFuture.get().getUpdateTime().toString();
    }

    private List<MessageOfDay> getMessages(String collectionName) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        List<MessageOfDay> messageList = new ArrayList<MessageOfDay>();

        CollectionReference messages = dbFirestore.collection(collectionName);

        for (DocumentReference document: messages.listDocuments()) {
            DocumentSnapshot documentSnapshot = null;
            MessageOfDay message;
            try {
                documentSnapshot = document.get().get();
                message = documentSnapshot.toObject(MessageOfDay.class);
                messageList.add(message);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return messageList;
    }

    private MessageOfDay sorterdMessage(List<MessageOfDay> messageList) {
        Random generator = new Random();
        Integer totalItens = messageList.size();
        Integer sorterdNumber = generator.nextInt(totalItens);

        return messageList.get(sorterdNumber);
    }

    private List<MessageOfDay> getMessageId(List<MessageOfDay> prevMessageList, MessageOfDay messageOfDay) {
        return prevMessageList
                .stream()
                .filter(Objects::nonNull)
                .filter(m -> m.getId() != null)
                .filter(m -> m.getId().equals(messageOfDay.getId()))
                .collect(Collectors.toList());
    }

    public MessageOfDay messageOfDay() {
        List<MessageOfDay> messageList = getMessages(COLLECTION_NAME_MESSAGES);
        List<MessageOfDay> prevMessageList = getMessages(COLLECTION_NAME_PREV_MESSAGES);

        MessageOfDay messageOfDay = sorterdMessage(messageList);
        List<MessageOfDay> prevMessage = getMessageId(prevMessageList, messageOfDay);

        while (prevMessage.size() > 0) {
            if (prevMessageList.size() == messageList.size()) {
                while (messageOfDay == null) {
                    messageOfDay = sorterdMessage(messageList);

                    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                    LocalDateTime dtDisplay = LocalDateTime.parse(messageOfDay.getDisplayDate(), formatter);
                    LocalDateTime today = LocalDateTime.now();

                    if (today.minusDays(messageList.size()).compareTo(dtDisplay) > 0) {
                        messageOfDay = null;
                    }
                }
            } else {
                messageOfDay = sorterdMessage(messageList);
                prevMessage = new ArrayList<MessageOfDay>();
                prevMessage = getMessageId(prevMessageList, messageOfDay);
            }
        }

        try {
            saveMessage(messageOfDay, COLLECTION_NAME_PREV_MESSAGES);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return messageOfDay;
    }

    public List<MessageOfDay> previousMessages(Integer start, Integer numberDisplay) {
        List<MessageOfDay> prevMessageList = getMessages(COLLECTION_NAME_PREV_MESSAGES);

        Collections.sort(prevMessageList, (a, b) -> a.getDisplayDate().compareTo(b.getDisplayDate()));
        if (prevMessageList.size() <= numberDisplay) {
            return prevMessageList;
        }
        return prevMessageList.subList(start, numberDisplay);
    }
}
