package kr.co._29cm.homework.cli;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDto {
    private final Long itemId;
    private final Integer itemCount;
}
