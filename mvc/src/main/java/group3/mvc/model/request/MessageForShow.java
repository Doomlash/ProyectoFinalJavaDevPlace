package group3.mvc.model.request;

import group3.mvc.model.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageForShow extends Message{
    private boolean gif;
    private String gifId;
    private double aspectRatio;
    public MessageForShow(Message m, boolean isGif, String gifId, double aspectRatio){
        super(m.getId(), m.getContent(), m.getLanguage(), m.getCreationDate(), m.getReceptionDate(), m.getReadDate(), m.getSenderId(), m.getReceiverId());
        this.gif = isGif;
        this.gifId=gifId;
        this.aspectRatio = aspectRatio;
    }
}
