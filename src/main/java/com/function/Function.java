package com.function;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

public class Function {

    @FunctionName("ProcessEventGridEvent")
    public void run(@EventGridTrigger(name = "eventGridEvent") String content,
            final ExecutionContext context) {

        context.getLogger().info("========================================");

        context.getLogger().info("Evento recibido desde Azure Event Grid");

        context.getLogger().info("========================================");

        try {

            // ==========================================
            // 1. Convertir el evento recibido a JSON
            // ==========================================

            JsonObject eventGridEvent = JsonParser.parseString(content).getAsJsonObject();

            // ==========================================
            // 2. Obtener tipo de evento
            // ==========================================

            String eventType = eventGridEvent.get("eventType").getAsString();

            context.getLogger().info("Tipo de evento: " + eventType);

            // ==========================================
            // 3. Obtener datos del evento
            // ==========================================

            JsonObject data = eventGridEvent.getAsJsonObject("data");

            context.getLogger().info("Datos recibidos: " + data);

            // ==========================================
            // 4. Procesar CitaCreada
            // ==========================================

            if ("CitaCreada".equals(eventType)) {

                String fechaCita = data.get("fechaCita").getAsString();

                long idUsuario = data.get("idUsuario").getAsLong();

                long idCliente = data.get("idCliente").getAsLong();

                long idMascota = data.get("idMascota").getAsLong();

                String estado = data.get("estado").getAsString();

                // ======================================
                // NOTIFICACIÓN
                // ======================================

                context.getLogger().info("========================================");

                context.getLogger().info("NOTIFICACIÓN - NUEVA CITA");

                context.getLogger().info("========================================");

                context.getLogger().info("Fecha cita: " + fechaCita);

                context.getLogger().info("Cliente: " + idCliente);

                context.getLogger().info("Mascota: " + idMascota);

                context.getLogger().info("Usuario: " + idUsuario);

                context.getLogger().info("Estado: " + estado);

                context.getLogger().info("========================================");

            } else {

                context.getLogger().info("Evento recibido pero no procesado: " + eventType);
            }

        } catch (Exception e) {

            context.getLogger().severe("Error procesando evento Event Grid: " + e.getMessage());

            throw e;
        }
    }
}
