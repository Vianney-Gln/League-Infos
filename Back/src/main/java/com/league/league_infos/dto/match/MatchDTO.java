package com.league.league_infos.dto.match;

public class MatchDTO {
    private MetadataDTO metadata;
    private InfoMatchDTO info;

    public MetadataDTO getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataDTO metadata) {
        this.metadata = metadata;
    }

    public InfoMatchDTO getInfo() {
        return info;
    }

    public void setInfo(InfoMatchDTO info) {
        this.info = info;
    }

    public static class Builder {
        private MetadataDTO metadata;
        private InfoMatchDTO info;

        public MatchDTO.Builder metadata(MetadataDTO metadata) {
            this.metadata = metadata;
            return this;
        }

        public MatchDTO.Builder info(InfoMatchDTO info) {
            this.info = info;
            return this;
        }

        public MatchDTO build() {
            MatchDTO dto = new MatchDTO();
            dto.setInfo(this.info);
            dto.setMetadata(this.metadata);
            return dto;
        }

    }
}
