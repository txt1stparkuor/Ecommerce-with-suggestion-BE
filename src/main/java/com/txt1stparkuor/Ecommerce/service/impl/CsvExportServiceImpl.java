package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.domain.entity.Product;
import com.txt1stparkuor.Ecommerce.repository.ProductRepository;
import com.txt1stparkuor.Ecommerce.service.CsvExportService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsvExportServiceImpl implements CsvExportService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public ByteArrayInputStream generateAmazonCsv() {
        final String[] headers = {
                "product_id", "product_name", "category", "discounted_price", "actual_price",
                "discount_percentage", "rating", "rating_count", "about_product", "user_id", "img_link"
        };

        final CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader(headers)
                .build();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {

            List<Product> products = productRepository.findAllWithReviewsAndUsers();

            for (Product product : products) {
                String userIds = product.getReviews().stream()
                        .map(review -> review.getUser().getId())
                        .distinct()
                        .collect(Collectors.joining("|"));

                String categoryPath = (product.getCategory() != null) ? product.getCategory().getFullPath() : "";

                String discountPercent = "0%";
                if (product.getOriginalPrice() != null && product.getPrice() != null && product.getOriginalPrice() > 0) {
                    double diff = product.getOriginalPrice() - product.getPrice();
                    double pct = (diff / product.getOriginalPrice()) * 100;
                    discountPercent = String.format("%.0f%%", pct);
                }

                csvPrinter.printRecord(
                        product.getId(),
                        product.getName(),
                        categoryPath,
                        product.getPrice(),
                        product.getOriginalPrice(),
                        discountPercent,
                        product.getAverageRating(),
                        product.getRatingCount(),
                        product.getDescription(),
                        userIds,
                        product.getImageUrl()
                );
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate CSV file: " + e.getMessage());
        }
    }
}


