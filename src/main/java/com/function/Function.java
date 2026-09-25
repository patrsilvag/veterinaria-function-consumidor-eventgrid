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

            JsonObject eventGridEvent =
                    JsonParser.parseString(content).getAsJsonObject();

            // ==========================================
            // 2. Obtener tipo de evento
            // ==========================================

            String eventType =
                    eventGridEvent.get("eventType").getAsString();

            context.getLogger().info("Tipo de evento: " + eventType);

            // ==========================================
            // 3. Obtener datos del evento
            // ==========================================

            JsonObject data =
                    eventGridEvent.getAsJsonObject("data");

            context.getLogger().info("Datos recibidos: " + data);

            // ==========================================
            // 4. Procesar RolCreado
            // ==========================================

            if ("RolCreado".equals(eventType)) {

                String nombreRol =
                        data.get("nombreRol").getAsString();

                String estado =
                        data.get("estado").getAsString();

                context.getLogger().info("========================================");
                context.getLogger().info("NOTIFICACIÓN - NUEVO ROL");
                context.getLogger().info("========================================");

                context.getLogger().info("Nombre rol: " + nombreRol);
                context.getLogger().info("Estado: " + estado);

                context.getLogger().info("========================================");

                return;
            }

            // ==========================================
            // 5. Procesar UsuarioCreado
            // ==========================================

            if ("UsuarioCreado".equals(eventType)) {

                long idUsuario =
                        data.get("idUsuario").getAsLong();

                long idRol =
                        data.get("idRol").getAsLong();

                String nombreUsuario =
                        data.get("nombreUsuario").getAsString();

                String email =
                        data.get("email").getAsString();

                String estado =
                        data.get("estado").getAsString();

                context.getLogger().info("========================================");
                context.getLogger().info("NOTIFICACIÓN - NUEVO USUARIO");
                context.getLogger().info("========================================");

                context.getLogger().info(
                        "ID Usuario: " + idUsuario);

                context.getLogger().info(
                        "ID Rol: " + idRol);

                context.getLogger().info(
                        "Nombre usuario: " + nombreUsuario);

                context.getLogger().info(
                        "Email: " + email);

                context.getLogger().info(
                        "Estado: " + estado);

                context.getLogger().info("========================================");

                return;
            }

            // ==========================================
            // 6. Procesar eventos de Citas
            // ==========================================

            if ("CitaCreada".equals(eventType)
                    || "CitaConfirmada".equals(eventType)
                    || "CitaCancelada".equals(eventType)) {

                // ==========================================
                // Datos específicos de Cita
                // ==========================================

                String fechaCita =
                        data.get("fechaCita").getAsString();

                long idCita =
                        data.get("idCita").getAsLong();

                long idUsuario =
                        data.get("idUsuario").getAsLong();

                long idCliente =
                        data.get("idCliente").getAsLong();

                long idMascota =
                        data.get("idMascota").getAsLong();

                String estado =
                        data.get("estado").getAsString();

                // ==========================================
                // 7. Procesar CitaCreada
                // ==========================================

                if ("CitaCreada".equals(eventType)) {

                    context.getLogger().info("========================================");
                    context.getLogger().info("NOTIFICACIÓN - NUEVA CITA");
                    context.getLogger().info("========================================");

                // ==========================================
                // 8. Procesar CitaConfirmada
                // ==========================================

                } else if ("CitaConfirmada".equals(eventType)) {

                    context.getLogger().info("========================================");
                    context.getLogger().info("NOTIFICACIÓN - CITA CONFIRMADA");
                    context.getLogger().info("========================================");

                // ==========================================
                // 9. Procesar CitaCancelada
                // ==========================================

                } else if ("CitaCancelada".equals(eventType)) {

                    context.getLogger().info("========================================");
                    context.getLogger().info("NOTIFICACIÓN - CITA CANCELADA");
                    context.getLogger().info("========================================");
                }

                // ==========================================
                // 10. Información de la cita
                // ==========================================

                context.getLogger().info("ID Cita: " + idCita);
                context.getLogger().info("Fecha cita: " + fechaCita);
                context.getLogger().info("Cliente: " + idCliente);
                context.getLogger().info("Mascota: " + idMascota);
                context.getLogger().info("Usuario: " + idUsuario);
                context.getLogger().info("Estado: " + estado);

                context.getLogger().info("========================================");

                return;
            }

            // ==========================================
            // 11. Evento no soportado
            // ==========================================

            context.getLogger().info(
                    "Evento recibido pero no procesado: " + eventType);

        } catch (Exception e) {

            context.getLogger().severe(
                    "Error procesando evento Event Grid: " + e.getMessage());

            throw e;
        }
    }
}