package com.distribuida.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ImagenController {
    private static final String UPLOAD_DIR = "uploads/portadas/";
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "gif", "webp"};

    @PostMapping("/upload-portada")
    public ResponseEntity<Map<String, String>> uploadPortada(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "oldImage", required = false) String oldImage) {

        try {
            // Validar que el archivo no esté vacío
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "El archivo está vacío"));
            }

            // Validar tipo de archivo
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);

            if (!isValidExtension(fileExtension)) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Tipo de archivo no permitido. Use: " +
                                String.join(", ", ALLOWED_EXTENSIONS)));
            }

            // Crear directorio si no existe
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Generar nombre único para el archivo
            String filename = UUID.randomUUID() + "_" + originalFilename;
            Path path = Paths.get(UPLOAD_DIR + filename); // ← CORREGIDO: UPLOAD_DIR ya tiene el slash

            // Guardar archivo
            Files.write(path, file.getBytes());

            // Eliminar imagen anterior si existe
            if (oldImage != null && !oldImage.isEmpty()) {
                try {
                    // Asumiendo que oldImage es solo el nombre del archivo (ej: "portadas/foto.jpg")
                    String oldFilename = oldImage.contains("/") ?
                            oldImage.substring(oldImage.lastIndexOf("/") + 1) : oldImage;

                    Path oldImagePath = Paths.get(UPLOAD_DIR + oldFilename);
                    Files.deleteIfExists(oldImagePath);
                } catch (IOException e) {
                    // No fallar si no se puede eliminar la imagen anterior
                    System.err.println("Error al eliminar imagen anterior: " + e.getMessage());
                }
            }

            Map<String, String> response = new HashMap<>();
            response.put("ruta", "portadas/" + filename); // ← Ruta relativa para el frontend
            response.put("mensaje", "Imagen subida correctamente");

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al subir imagen: " + e.getMessage()));
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    private boolean isValidExtension(String extension) {
        for (String allowed : ALLOWED_EXTENSIONS) {
            if (allowed.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
