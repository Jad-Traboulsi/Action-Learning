package fr.epita.actionlearning.DTOs;

public class MessageDTO {
    private String responseMsg;
    private int response;

    public MessageDTO() {
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "responseMsg='" + responseMsg + '\'' +
                ", response=" + response +
                '}';
    }
}
