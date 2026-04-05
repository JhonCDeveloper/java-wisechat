<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resultado del Registro - Wisechat</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 flex h-screen items-center justify-center">
    <div class="bg-white p-8 rounded-lg shadow-lg max-w-sm w-full text-center">
        <% 
            Boolean exito = (Boolean) request.getAttribute("exito");
            String mensaje = (String) request.getAttribute("mensaje");
            
            if (exito != null && exito) { 
        %>
            <div class="mx-auto flex items-center justify-center h-12 w-12 rounded-full bg-green-100 mb-4">
                <svg class="h-6 w-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                </svg>
            </div>
            <h2 class="text-2xl font-extrabold text-gray-900 mb-2">¡Éxito!</h2>
        <% } else { %>
            <div class="mx-auto flex items-center justify-center h-12 w-12 rounded-full bg-red-100 mb-4">
                <svg class="h-6 w-6 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                </svg>
            </div>
            <h2 class="text-2xl font-extrabold text-gray-900 mb-2">Error</h2>
        <% } %>
        
        <p class="text-gray-600 mb-8 font-medium">
            <%= (mensaje != null) ? mensaje : "Ocurrió un error inesperado. Por favor, inténtalo de nuevo." %>
        </p>

        <a href="registro.jsp" 
           class="inline-block w-full py-3 px-4 border border-transparent rounded-md shadow-sm text-sm font-bold text-white <%=(exito != null && exito) ? "bg-green-600 hover:bg-green-700 focus:ring-green-500" : "bg-red-600 hover:bg-red-700 focus:ring-red-500"%> focus:outline-none focus:ring-2 focus:ring-offset-2 transition-colors duration-200">
            Volver al Inicio
        </a>
    </div>
</body>
</html>
