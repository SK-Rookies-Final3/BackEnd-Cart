package com.cart.service;

import com.cart.db.CustomCart;
import com.cart.db.CustomCartItem;
import com.cart.db.CustomCartItemRepository;
import com.cart.db.CustomCartRepository;
import com.cart.dto.CartItemPositionUpdateDto;
import com.cart.dto.CustomCartDto;
import com.cart.dto.CustomCartItemDto;
import com.cart.exception.CustomCartNotFoundException;
import com.cart.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomCartService {

    private final CustomCartRepository customCartRepository;
    private final CustomCartItemRepository customCartItemRepository;  // CustomCartItemRepository 추가

    @Autowired
    public CustomCartService(CustomCartRepository customCartRepository, CustomCartItemRepository customCartItemRepository) {
        this.customCartRepository = customCartRepository;
        this.customCartItemRepository = customCartItemRepository;  // repository 주입
    }

    private CustomCartItemDto convertToDto(CustomCartItem customCartItem) {
        CustomCartItemDto customCartItemDto = new CustomCartItemDto();
        customCartItemDto.setProductCode(customCartItem.getProductCode());
        customCartItemDto.setQuantity(customCartItem.getQuantity());
        return customCartItemDto;
    }

    private CustomCartDto convertToDto(CustomCart customCart) {
        CustomCartDto customCartDto = new CustomCartDto();
        customCartDto.setUserId(customCart.getUserId());
        customCartDto.setCustomCartTitle(customCart.getCustomCartTitle());
        List<CustomCartItemDto> itemDtos = customCart.getItems().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        customCartDto.setItems(itemDtos);
        return customCartDto;
    }

    public CustomCartDto getCustomCart(String userId) {
        Optional<CustomCart> customCart = customCartRepository.findByUserId(userId);
        if (customCart.isPresent()) {
            return convertToDto(customCart.get());
        } else {
            throw new CustomCartNotFoundException("Custom cart not found for user: " + userId);
        }
    }

    public CustomCartDto createCustomCart(CustomCartDto customCartDto, String userId) {
        if (customCartDto.getCustomCartTitle() == null || customCartDto.getCustomCartTitle().isEmpty()) {
            String baseTitle = "My Custom Cart";
            String generatedTitle = generateUniqueTitle(baseTitle);
            customCartDto.setCustomCartTitle(generatedTitle);
        } else {
            String uniqueTitle = generateUniqueTitle(customCartDto.getCustomCartTitle());
            customCartDto.setCustomCartTitle(uniqueTitle);
        }

        customCartDto.setUserId(userId);

        CustomCart customCart = new CustomCart();
        customCart.setUserId(customCartDto.getUserId());
        customCart.setCustomCartTitle(customCartDto.getCustomCartTitle());

        List<CustomCartItem> items = customCartDto.getItems().stream()
                .map(this::convertToDbEntity)
                .collect(Collectors.toList());
        customCart.setItems(items);

        CustomCart savedCart = customCartRepository.save(customCart);
        return convertToDto(savedCart);
    }

    private CustomCartItem convertToDbEntity(CustomCartItemDto customCartItemDto) {
        CustomCartItem customCartItem = new CustomCartItem();
        customCartItem.setProductCode(customCartItemDto.getProductCode());
        customCartItem.setQuantity(customCartItemDto.getQuantity());
        return customCartItem;
    }

    public void updateCartItemPosition(CartItemPositionUpdateDto dto) {
        // 아이템 ID로 기존 장바구니 항목을 찾음
        CustomCartItem item = customCartItemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // 아이템의 좌표를 업데이트
        item.setXCoordinate(dto.getXCoordinate());
        item.setYCoordinate(dto.getYCoordinate());

        // 데이터베이스에 변경사항 저장
        customCartItemRepository.save(item);  // CustomCartItemRepository에 저장
    }

    public CustomCartDto updateCustomCartTitle(String newTitle, String userId) {
        Optional<CustomCart> customCart = customCartRepository.findByUserId(userId);
        if (customCart.isPresent()) {
            CustomCart cart = customCart.get();
            cart.setCustomCartTitle(newTitle);
            customCartRepository.save(cart);
            return convertToDto(cart);
        } else {
            throw new CustomCartNotFoundException("Custom cart not found for user: " + userId);
        }
    }

    public CustomCartDto removeItemFromCustomCart(String productCode, String userId) {
        Optional<CustomCart> customCart = customCartRepository.findByUserId(userId);
        if (customCart.isPresent()) {
            CustomCart cart = customCart.get();
            List<CustomCartItem> items = cart.getItems();
            CustomCartItem itemToRemove = null;
            for (CustomCartItem item : items) {
                if (item.getProductCode().equals(productCode)) {
                    itemToRemove = item;
                    break;
                }
            }
            if (itemToRemove != null) {
                items.remove(itemToRemove);
                customCartRepository.save(cart);
                return convertToDto(cart);
            } else {
                throw new ItemNotFoundException("Item not found in custom cart");
            }
        } else {
            throw new CustomCartNotFoundException("Custom cart not found for user: " + userId);
        }
    }

    private String generateUniqueTitle(String baseTitle) {
        String newTitle = baseTitle;
        int counter = 1;
        while (customCartRepository.findByCustomCartTitle(newTitle).isPresent()) {
            newTitle = baseTitle + " (" + counter + ")";
            counter++;
        }
        return newTitle;
    }
}
