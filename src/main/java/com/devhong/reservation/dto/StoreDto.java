package com.devhong.reservation.dto;

import com.devhong.reservation.model.Store;
import com.devhong.reservation.model.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StoreDto {

    @Data
    public static class Registration{
        @NotNull
        private Long userId;

        @NotBlank
        private String storeName;

        @NotBlank
        private String location;

        @NotBlank
        private String description;

        public Store toEntity(User user) {
            return Store.builder()
                    .user(user)
                    .storeName(storeName)
                    .location(location)
                    .description(description)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RegistrationResponse{
        private String storeName;
        private String location;

        public static RegistrationResponse fromEntity(Store store) {
            return RegistrationResponse.builder()
                    .storeName(store.getStoreName())
                    .location(store.getLocation())
                    .build();
        }
    }

    @Data
    @Builder
    public static class SearchResponse{
        private Long storeId;
        private String storeName;
        private String location;
        private String description;

        public static SearchResponse fromEntity(Store store){
            return SearchResponse.builder()
                    .storeId(store.getId())
                    .storeName(store.getStoreName())
                    .location(store.getLocation())
                    .description(store.getDescription())
                    .build();
        }
    }
}
