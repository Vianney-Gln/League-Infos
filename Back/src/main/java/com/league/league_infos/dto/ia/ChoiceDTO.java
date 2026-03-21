package com.league.league_infos.dto.ia;

public class ChoiceDTO {
    private int index;
    private MessageDTO message;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public MessageDTO getMessage() {
        return message;
    }

    public void setMessage(MessageDTO message) {
        this.message = message;
    }

    public static class Builder {
        private int index;
        private MessageDTO message;

        public ChoiceDTO.Builder index(int index) {
            this.index = index;
            return this;
        }

        public ChoiceDTO.Builder message(MessageDTO message) {
            this.message = message;
            return this;
        }

        public ChoiceDTO build() {
            ChoiceDTO choiceDTO = new ChoiceDTO();
            choiceDTO.setIndex(index);
            choiceDTO.setMessage(message);
            return choiceDTO;
        }
    }
}
