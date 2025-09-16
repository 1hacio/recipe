// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/util/InventoryDataLoader.java

package com.recipick.backend.util;

import com.recipick.backend.model.Inventory;
import com.recipick.backend.repository.InventoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class InventoryDataLoader {

    @Autowired
    private InventoryRepository inventoryRepository;

    @PostConstruct
    public void loadInitialInventory() {
        // inventory 테이블에 데이터가 이미 있으면 실행하지 않음
        if (inventoryRepository.count() > 0) {
            return;
        }

        // 프론트엔드의 더미 데이터를 기반으로 Inventory 객체 생성
        Inventory item1 = new Inventory();
        item1.setProductId("USER002");
        item1.setProductName("또띠아");
        item1.setAliases("tortilla");
        item1.setAmountType("step");
        item1.setAmountLevel("full");
        item1.setExpirationDate(LocalDate.of(2025, 6, 20));
        item1.setPurchaseDate(LocalDate.of(2025, 6, 23));
        item1.setImageUrl("http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcTh6zUJ2mXogKhNgMZyIqMBBHeZB7BiKTKHtl51ejFlvIQmvAYZ9inzxRcym57p6o5_04S6JHz71bC_Z4962q4");

        Inventory item2 = new Inventory();
        item2.setProductId("USER001");
        item2.setProductName("찌개용 돼지고기");
        item2.setAliases("pork,samgyeopsal, Potato starch");
        item2.setAmountType("step");
        item2.setAmountLevel("half");
        item2.setExpirationDate(LocalDate.of(2025, 6, 20));
        item2.setMemo("오늘 저녁 메뉴!");
        item2.setPurchaseDate(LocalDate.of(2026, 6, 23));
        item2.setImageUrl("https://oasisprodproduct.edge.naverncp.com/101939/detail/0_c43f2071-7994-4b16-87fc-aae0712174bc.jpg");

        Inventory item3 = new Inventory();
        item3.setProductId("P006");
        item3.setProductName("고추");
        item3.setAliases("pepper, red pepper");
        item3.setAmountType("count");
        item3.setAmountValue(12);
        item3.setExpirationDate(LocalDate.of(2025, 6, 11));

        Inventory item4 = new Inventory();
        item4.setProductId("P008");
        item4.setProductName("감자");
        item4.setAliases("potatoes");
        item4.setAmountType("exact");
        item4.setAmountValue(120);
        item4.setAmountUnit("kg");
        item4.setExpirationDate(LocalDate.of(2025, 6, 17));

        Inventory item5 = new Inventory();
        item5.setProductId("P011");
        item5.setProductName("된장");
        item5.setAliases("corn tortillas");
        item5.setAmountType("exact");
        item5.setAmountValue(1000);
        item5.setAmountUnit("g");
        item5.setExpirationDate(LocalDate.of(2025, 6, 17));

        // 생성된 객체들을 데이터베이스에 저장
        inventoryRepository.saveAll(List.of(item1, item2, item3, item4, item5));
    }
}