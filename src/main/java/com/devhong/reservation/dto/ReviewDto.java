package com.devhong.reservation.dto;

import com.devhong.reservation.model.Review;
import com.devhong.reservation.model.Store;
import com.devhong.reservation.model.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ReviewDto {

    @Data
    public static class ReviewRequest{
        @NotBlank
        private String username;

        @NotNull
        private Long storeId;

        @NotBlank
        private String review;

        @Min(1)
        @Max(5)
        private Long rate;

        public Review toEntity(User user, Store store) {
            return Review.builder()
                    .user(user)
                    .store(store)
                    .review(review)
                    .rate(rate)
                    .build();
        }
    }

    @Data
    @Builder
    public static class ReviewResponse{
        private String userName;
        private String storeName;
        private String review;
        private Long rate;

        public static ReviewResponse fromEntity(Review review) {
            return ReviewResponse.builder()
                    .userName(review.getUser().getUsername())
                    .storeName(review.getStore().getStoreName())
                    .review(review.getReview())
                    .rate(review.getRate())
                    .build();
        }
    }
}
