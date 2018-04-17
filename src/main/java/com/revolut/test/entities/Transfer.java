package com.revolut.test.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    private int idFrom;

    private int idTo;

    private int amount;
}
