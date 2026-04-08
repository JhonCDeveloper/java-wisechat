import { useState } from "react";
/* import { Link } from "react-router-dom"; */
import { Link, useNavigate } from "react-router-dom";
import logo from "../assets/LogoWiseChat.png";

export default function Form() {

  const navigate = useNavigate();

  const [step, setStep] = useState(0);
  const [formData, setFormData] = useState({
    negocio: "",
    rol: "",
    sector: "",
    productos: "",
    dondeVendes: "",
    cliente: "",
    mensajes: "",
    herramientas: "",
    dificultad: "",
    equipo: "",
    sugerencias: "",
  });

  const preguntas = [
    {
      label: "¿CÓMO SE LLAMA TU NEGOCIO?",
      type: "input",
      name: "negocio",
    },
    {
      label: "¿CUÁL ES TU NOMBRE Y TU ROL EN LA EMPRESA? EJM: (JHON, CEO)",
      type: "input",
      name: "rol",
    },
    {
      label: "¿A QUÉ SECTOR PERTENECE TU NEGOCIO?",
      type: "select",
      name: "sector",
      options: [
        "Alimentos y bebidas",
        "Moda y accesorios",
        "Belleza",
        "Servicios",
        "Tecnología",
        "Banca",
        "Otro",
      ],
    },
    {
      label: "¿QUÉ PRODUCTOS O SERVICIOS OFRECES?",
      type: "input",
      name: "productos",
    },
    {
      label: "¿DÓNDE VENDES TUS PRODUCTOS O SERVICIOS?",
      type: "select",
      name: "dondeVendes",
      options: [
        "Solo en redes sociales",
        "Página web propia",
        "Tienda física",
        "Marketplaces (Mercado Libre, Amazon, etc.)",
        "WhatsApp u otras apps de mensajería",
        "Otro",
      ],
    },
    {
      label:
        "¿CÓMO DESCRIBIRÍAS A TU CLIENTE IDEAL? (EDAD, INTERÉSES, UBICACIÓN...)",

      type: "input",
      name: "cliente",
    },
    {
      label: "¿CUÁNTOS MENSAJES PROMEDIO RECIBES AL DÍA DE POTENCIALES CLIENTES?",
      type: "select",
      name: "mensajes",
      options: ["Menos de 10", "Entre 10 y 30", "Más de 30", "Varía mucho"],
    },
    {
      label: "¿QUÉ HERRAMIENTAS USAS ACTUALMENTE PARA COMUNICARTE CON TUS CLIENTES?",
      type: "select",
      name: "herramientas",
      options: [
        "WhatsApp Business",
        "Instagram/Facebook Inbox",
        "CRM",
        "Ninguna",
        "Otra",
      ],
    },
    {
      label: "¿CUÁL ES EL PASO MÁS DIFÍCIL EN TU PROCESO DE VENTA?",
      type: "select",
      name: "dificultad",
      options: [
        "Captar la atención de clientes nuevos",
        "Recibir mensajes o consultas",
        "Responder rápidamente",
        "Cerrar la venta",
        "Hacer seguimiento",
        "Otra",
      ],
    },
    {
      label: "¿TIENES UN EQUIPO QUE TE AYUDA CON VENTAS O ATENCIÓN AL CLIENTE?",
      type: "select+input",
      name: "equipo",
      options: [
        "Sí (¿cuántas personas?)",
        "No, lo hago todo solo(a)",
      ],
      placeholder: "Especifica cuántas personas",
    },
    {
      label:
        "¿TE GUSTARÍA QUE WISECHAT TE SUGIERA RESPUESTAS AUTOMÁTICAS, EMBUDOS PERSONALIZADOS Y CONTENIDO?",
      type: "select",
      name: "sugerencias",
      options: ["Sí", "No", "Tal vez, depende de qué tan útil sea"],
    },
  ];

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleNext = () => {
    if (step < preguntas.length - 1) {
      setStep(step + 1);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("✅ Datos enviados:", formData);
    alert("Formulario enviado con éxito 🚀");

    navigate("/dashboard");
  };

  const current = preguntas[step];

  return (
    <div className="min-h-screen bg-blackbg flex flex-col items-center justify-center font-montserrat relative px-4">
      {/* Logo */}
      <div className="absolute top-4 left-4">
        <Link to="/">
          <img src={logo} alt="Logo WiseChat" className="w-[200px] h-[60px]" />
        </Link>
      </div>

      {/* Título */}
      <h1 className="text-whitewc text-center text-2xl md:text-4xl font-bold mb-6 mt-16">
        ¿LISTO PARA{" "}
        <span className="text-wisepurple">POTENCIAR</span> TUS VENTAS?
        <br />
        <em className="text-wisepurple font-light">EMPECEMOS AQUÍ</em>
      </h1>

      {/* Formulario */}
      <form
        onSubmit={handleSubmit}
        className="bg-[#1C1C1E] rounded-2xl shadow-lg p-6 w-full max-w-lg text-whitewc"
      >
        <label className="block mb-2 font-semibold">{current.label}</label>

        {/* Input dinámico */}
        {current.type === "input" && (
          <input
            type="text"
            name={current.name}
            value={formData[current.name]}
            onChange={handleChange}
            required
            className="w-full p-2 rounded-md bg-blackbg text-whitewc outline-none"
          />
        )}

        {current.type === "select" && (
          <select
            name={current.name}
            value={formData[current.name]}
            onChange={handleChange}
            required
            className="w-full p-2 rounded-md bg-blackbg text-whitewc outline-none"
          >
            <option value="">Selecciona una opción</option>
            {current.options.map((opt, i) => (
              <option key={i} value={opt}>
                {opt}
              </option>
            ))}
          </select>
        )}

        {current.type === "select+input" && (
          <div className="flex flex-col gap-3">
            <select
              name={current.name}
              value={formData[current.name]}
              onChange={handleChange}
              required
              className="w-full p-2 rounded-md bg-blackbg text-whitewc outline-none"
            >
              <option value="">Selecciona una opción</option>
              {current.options.map((opt, i) => (
                <option key={i} value={opt}>
                  {opt}
                </option>
              ))}
            </select>
            <input
              type="text"
              placeholder={current.placeholder}
              name="equipoDetalle"
              value={formData.equipoDetalle || ""}
              onChange={handleChange}
              className="w-full p-2 rounded-md bg-blackbg text-whitewc outline-none"
            />
          </div>
        )}

        {/* Botón siguiente o submit */}
        {step < preguntas.length - 1 ? (
          <button
            type="button"
            onClick={handleNext}
            className="bg-wisepurple hover:bg-purple-700 text-whitewc font-semibold py-2 px-6 rounded-md mt-4 w-full"
          >
            Siguiente
          </button>
        ) : (
          <button
            type="submit"
            className="bg-wisepurple hover:bg-purple-700 text-whitewc font-semibold py-2 px-6 rounded-md mt-4 w-full"
          >
            Finalizar
          </button>
        )}
      </form>
    </div>
  );
}
