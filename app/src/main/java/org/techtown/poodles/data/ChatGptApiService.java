package org.techtown.poodles;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatGptApiService {


    @POST()
    void getIdeation(
            @Body
    )
}
