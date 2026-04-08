

export default function Support() {
  const faqs = [
    {
      question: "¿Cómo puedo empezar a usar Wisechat?",
      answer:
        "Solo debes registrarte, completar el perfil de tu negocio y empezar a interactuar con nuestro bot para recibir sugerencias personalizadas.",
    },
    {
      question: "¿Wisechat tiene algún costo?",
      answer:
        "La versión básica es totalmente gratuita. Pronto tendremos planes premium con más funcionalidades avanzadas.",
    },
    {
      question: "¿Puedo contactar soporte directamente?",
      answer:
        "Sí, en cualquier momento puedes enviarnos un mensaje a soporte@wisechat.com y nuestro equipo te ayudará.",
    },
  ];

  return (
    <div className="flex flex-col h-full bg-gray-900 rounded-xl shadow-lg overflow-hidden">
      {/* HEADER */}
      <div className="bg-wisepurple p-4 text-white font-bold text-lg">
        Soporte
      </div>

      {/* CONTENIDO */}
      <div className="flex-1 p-6 overflow-y-auto">
        <h2 className="text-2xl font-bold text-white mb-6">
          Preguntas Frecuentes (FAQ)
        </h2>

        <div className="space-y-4">
          {faqs.map((faq, i) => (
            <div key={i} className="bg-gray-800 rounded-lg p-4">
              <h3 className="text-lg font-semibold text-wisepurple">
                {faq.question}
              </h3>
              <p className="text-gray-300 mt-2">{faq.answer}</p>
            </div>
          ))}
        </div>

        {/* CONTACTO */}
        <div className="mt-10 bg-gray-800 rounded-lg p-6 text-center">
          <h3 className="text-xl font-bold text-white mb-4">
            ¿Aún necesitas ayuda?
          </h3>
          <p className="text-gray-300 mb-4">
            Escríbenos y nuestro equipo de soporte estará disponible para ti.
          </p>
          <a
            href="mailto:soporte@wisechat.com"
            className="px-6 py-3 bg-wisepurple text-white rounded-lg font-semibold hover:bg-purple-700 transition"
          >
            Contactar Soporte
          </a>
        </div>
      </div>
    </div>
  );
}