package Servicios;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebService(serviceName = "WSprueba")
public class WSprueba {

    @WebMethod(operationName = "getAlumno")
    public String getAlumno(@WebParam(name = "id") int id) {
        // Variables para almacenar los datos del alumno
        String nombre = "";
        int edad = 0;
        String direccion = "";
        String carrera = "";
        String rfc = "";

        // Realizar la consulta a la base de datos
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT nombre, edad, direccion, carrera, rfc FROM Alumnos WHERE id = " + id;
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    nombre = resultSet.getString("nombre");
                    edad = resultSet.getInt("edad");
                    direccion = resultSet.getString("direccion");
                    carrera = resultSet.getString("carrera");
                    rfc = resultSet.getString("rfc");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al obtener los datos del alumno.";
        }

        // Construir la respuesta con los datos del alumno
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("Nombre: ").append(nombre).append("\n");
        responseBuilder.append("Edad: ").append(edad).append("\n");
        responseBuilder.append("Dirección: ").append(direccion).append("\n");
        responseBuilder.append("Carrera: ").append(carrera).append("\n");
        responseBuilder.append("RFC: ").append(rfc);

        return responseBuilder.toString();
    }
}
