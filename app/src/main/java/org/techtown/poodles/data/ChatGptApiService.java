package org.techtown.poodles.data;

import org.techtown.poodles.data.request.ChatGptRequest;
import org.techtown.poodles.data.response.ResponseChatGPT;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatGptApiService {
    @POST("v1/chat/completions")
    Call<ResponseChatGPT> getIdeation(
            @Body ChatGptRequest chatGptRequest
    );

    @POST("v1/chat/completions")
    Call<ResponseChatGPT> getRandomIdea(
            @Body ChatGptRequest chatGptRequest
    );
}
