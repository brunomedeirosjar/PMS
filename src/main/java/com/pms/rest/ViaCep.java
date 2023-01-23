package com.pms.rest;

import com.pms.dto.ViaCepDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ViaCep", url = "https://viacep.com.br/ws")
public interface ViaCep {
    @GetMapping(value = "/{postalCode}/json")
    ViaCepDto search(@PathVariable("postalCode") String cep);
}
