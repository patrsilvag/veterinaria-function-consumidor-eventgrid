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
            // 4. Datos comunes
            // ==========================================

            String fechaCita = data.get("fechaCita").getAsString();

            long idCita = data.get("idCita").getAsLong();

            long idUsuario = data.get("idUsuario").getAsLong();

            long idCliente = data.get("idCliente").getAsLong();

            long idMascota = data.get("idMascota").getAsLong();

            String estado = data.get("estado").getAsString();

            // ==========================================
            // 5. Procesar CitaCreada
            // ==========================================

            if ("CitaCreada".equals(eventType)) {

                context.getLogger().info("========================================");

                context.getLogger().info("NOTIFICACIÓN - NUEVA CITA");

                context.getLogger().info("========================================");

                // ==========================================
                // 6. Procesar CitaConfirmada
                // ==========================================

            } else if ("CitaConfirmada".equals(eventType)) {

                context.getLogger().info("========================================");

                context.getLogger().info("NOTIFICACIÓN - CITA CONFIRMADA");

                context.getLogger().info("========================================");

                // ==========================================
                // 7. Procesar CitaCancelada
                // ==========================================

            } else if ("CitaCancelada".equals(eventType)) {

                context.getLogger().info("========================================");

                context.getLogger().info("NOTIFICACIÓN - CITA CANCELADA");

                context.getLogger().info("========================================");

                // ==========================================
                // 8. Evento no soportado
                // ==========================================

            } else {

                context.getLogger().info("Evento recibido pero no procesado: " + eventType);

                return;
            }

            // ==========================================
            // 9. Información de la cita
            // ==========================================

            context.getLogger().info("ID Cita: " + idCita);

            context.getLogger().info("Fecha cita: " + fechaCita);

            context.getLogger().info("Cliente: " + idCliente);

            context.getLogger().info("Mascota: " + idMascota);

            context.getLogger().info("Usuario: " + idUsuario);

            context.getLogger().info("Estado: " + estado);

            context.getLogger().info("========================================");

        } catch (Exception e) {

            context.getLogger().severe("Error procesando evento Event Grid: " + e.getMessage());

            throw e;
        }
    }
}
