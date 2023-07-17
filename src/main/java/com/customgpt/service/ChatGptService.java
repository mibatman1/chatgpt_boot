package com.customgpt.service;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.gjsm.api.openai.OpenAiClient;
import cn.gjsm.api.openai.OpenAiClientFactory;
import cn.gjsm.api.pojo.chat.ChatCompletionRequest;
import cn.gjsm.api.pojo.chat.ChatCompletionResponse;
import cn.gjsm.api.pojo.chat.ChatMessage;
import retrofit2.Call;
import retrofit2.Response;

@Service
public class ChatGptService 
{
    public String chatService(String query) throws IOException
    {
         //Instance the OpenAiClient using the "createClient()" and passing the API Key/Token.
        OpenAiClient openAiClient = OpenAiClientFactory.createClient("");

        //Instance the message, setting the role as a user and passing the user query.
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRole("user");
        chatMessage.setContent(query);

        //Instance the request, setting the messages field and model of GPT.
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .messages(Arrays.asList(chatMessage))
                .model("gpt-3.5-turbo")
                .build();

        //Execute request, making a network call using the callChatCompletion().
        //Passing the request into it.
        Call<ChatCompletionResponse> chatCompletion = openAiClient.callChatCompletion(request);
        Response<ChatCompletionResponse> response = chatCompletion.execute();

        //returning the generated response 
        if (response.isSuccessful()) 
        {
            String result=JSON.toJSONString(response.body());
            ObjectMapper objectMapper=new ObjectMapper();

            //Parsing the JSON string into a JsonNode object
            JsonNode jsonNode=objectMapper.readTree(result);

            //Navigating to the message field
            JsonNode msgJsonNode=jsonNode.at("/choices/0/message");

            //Extracting the content value
            String msgValue=msgJsonNode.get("content").asText();
            return msgValue;
        }
        return "Something went wrong";
    }
}
