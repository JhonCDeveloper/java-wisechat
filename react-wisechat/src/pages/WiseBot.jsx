
import { useState } from "react";

export default function WiseBot() {
  const [messages, setMessages] = useState([
    { sender: "bot", text: "¡Hola! Soy WiseBot 🤖. ¿En qué puedo ayudarte hoy?" },
  ]);
  const [input, setInput] = useState("");

  const sendMessage = () => {
    if (!input.trim()) return;

    // Agregar mensaje del usuario
    const newMessage = { sender: "user", text: input };
    setMessages((prev) => [...prev, newMessage]);

    // Respuesta Demo del bot (placeholder)
    setTimeout(() => {
      setMessages((prev) => [
        ...prev,
        { sender: "bot", text: "Estoy procesando tu mensaje..." },
      ]);
    }, 800);

    setInput("");
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") sendMessage();
  };

  return (
    <div className="flex flex-col h-full bg-gray-900 rounded-xl shadow-lg overflow-hidden">
      {/* HEADER */}
      <div className="bg-wisepurple p-4 text-white font-bold text-lg">
        WiseBot
      </div>

      {/* CHAT */}
      <div className="flex-1 p-4 overflow-y-auto space-y-4">
        {messages.map((msg, index) => (
          <div
            key={index}
            className={`flex ${
              msg.sender === "user" ? "justify-end" : "justify-start"
            }`}
          >
            <div
              className={`px-4 py-2 rounded-lg max-w-[70%] ${
                msg.sender === "user"
                  ? "bg-wisepurple text-white"
                  : "bg-gray-800 text-gray-100"
              }`}
            >
              {msg.text}
            </div>
          </div>
        ))}
      </div>

      {/* INPUT */}
      <div className="p-4 border-t border-gray-700 flex gap-2">
        <input
          type="text"
          placeholder="Escribe tu mensaje..."
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={handleKeyDown}
          className="flex-1 px-4 py-2 rounded-lg bg-gray-800 text-white focus:outline-none"
        />
        <button
          onClick={sendMessage}
          className="px-6 py-2 bg-wisepurple rounded-lg text-white font-semibold hover:bg-purple-700 transition"
        >
          Enviar
        </button>
      </div>
    </div>
  );
}