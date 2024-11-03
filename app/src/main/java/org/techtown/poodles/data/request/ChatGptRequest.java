package org.techtown.poodles.data;

import com.google.gson.annotations.SerializedName;

public class ChatGptRequest {
    public String model = "gpt-3.5-turbo-1106";
    @SerializedName("response_format")
    public ResponseModel responseModel = new ResponseModel();
    public MessageModel[] messages;


    public ChatGptRequest() {

    }

    public ChatGptRequest(String keywords) {
        this.messages = new MessageModel[]{
                new MessageModel(
                        "system",
                        "너는 제공된 키워드에 대해 아이디어를 제공하는 챗봇이고 Json 형식의 Output을 제공할 수 있어."
                ),
                new MessageModel(
                        "system",
                        "Json Output의 String Type의 title, String type의 description 필드를 갖고있는 Object의 배열로 구성 돼."
                ),
                new MessageModel(
                        "user",
                        "너에게 몇가지 단어를 줄거야\n" +
                                "이 단어들을 활용하여 3가지 아이디어를 추천해줘.\n" +
                                "각 아이디어는 title과 간단한 description으로 이루어져 있어야 돼.\n" +
                                "다음 문장에는 아이디어 도출을 위한 단어들이 적혀있어.\n" +
                                keywords
                )
        };
    }
}
