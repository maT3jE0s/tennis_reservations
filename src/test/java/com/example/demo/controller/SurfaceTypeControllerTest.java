package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.SurfaceTypeRequest;
import com.example.demo.entity.SurfaceType;
import com.example.demo.service.SurfaceTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SurfaceTypeController.class)
public class SurfaceTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SurfaceTypeService surfaceTypeService;

    private SurfaceType surfaceType;

    @BeforeEach
    void setup() {
        surfaceType = new SurfaceType("Grass", 0.5);
        surfaceType.setId(1L);
    }

    @Test
    void testCreate() throws Exception {
        SurfaceTypeRequest request = new SurfaceTypeRequest("Grass", 0.5);

        when(surfaceTypeService.create(any())).thenReturn(surfaceType);

        mockMvc.perform(post("/api/surface-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Grass"))
                .andExpect(jsonPath("$.pricePerMinute").value(0.5));
    }

    @Test
    void testGetAll() throws Exception {
        when(surfaceTypeService.getAll()).thenReturn(List.of(surfaceType));

        mockMvc.perform(get("/api/surface-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Grass"))
                .andExpect(jsonPath("$[0].pricePerMinute").value(0.5));
    }

    @Test
    void testGetById() throws Exception {
        when(surfaceTypeService.getById(1L)).thenReturn(surfaceType);

        mockMvc.perform(get("/api/surface-types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Grass"))
                .andExpect(jsonPath("$.pricePerMinute").value(0.5));
    }

    @Test
    void testUpdate() throws Exception {
        SurfaceTypeRequest request = new SurfaceTypeRequest("Grass", 0.5);
        when(surfaceTypeService.update(eq(1L), any())).thenReturn(surfaceType);

        mockMvc.perform(put("/api/surface-types/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Grass"))
                .andExpect(jsonPath("$.pricePerMinute").value(0.5));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(surfaceTypeService).delete(1L);

        mockMvc.perform(delete("/api/surface-types/1"))
                .andExpect(status().isNoContent());
    }
}
