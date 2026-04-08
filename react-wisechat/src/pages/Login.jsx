import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from "../assets/LogoWiseChat.png";

export default function Login() {
  const navigate = useNavigate();

  // Estados
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  // Usuarios en localStorage (si no hay, se cargan los predeterminados)
  useEffect(() => {
    const storedUsers = JSON.parse(localStorage.getItem("users"));
    if (!storedUsers) {
      const defaultUsers = [
        { email: "Yuranytamara@gmail.com", password: "Tamara123" },
        { email: "jairovalencia100K19@gmail.com", password: "100K1718" },
        { email: "isagoz1708@gmail.com", password: "Isa123" },
      ];
      localStorage.setItem("users", JSON.stringify(defaultUsers));
    }
  }, []);

  const authenticate = (email, password) => {
    const users = JSON.parse(localStorage.getItem("users")) || [];
    return users.some((user) => user.email === email && user.password === password);
  };

  const handleLogin = (e) => {
    e.preventDefault();

    if (authenticate(email.trim(), password.trim())) {
      localStorage.setItem("currentUser", email.trim());
      setMessage("✅ Inicio de sesión exitoso, redirigiendo...");
      setTimeout(() => navigate("/form"), 1500); // Redirige al formulario
    } else {
      setMessage("❌ Correo o contraseña incorrectos");
    }
  };

  return (
    <div className="min-h-screen bg-blackbg flex flex-col items-center font-montserrat">
      {/* Encabezado */}
      <header className="flex justify-center items-center mt-6">
        <Link to="/">
          <img
            src={logo}
            alt="Logo WiseChat"
            className="w-[300px] h-[75px] object-contain"
          />
        </Link>
      </header>

      {/* Formulario */}
      <div className="flex flex-col items-center mt-10 w-full px-4">
        {/* Botón Registrar */}
        <div className="mb-6">
          <Link
            to="/register"
            className="bg-wisepurple hover:bg-purple-700 text-whitewc text-lg font-semibold px-6 py-2 rounded-lg"
          >
            REGISTRARME
          </Link>
        </div>

        {/* Caja del formulario */}
        <div className="bg-[#1C1C1E] rounded-lg shadow-lg p-6 w-full max-w-sm">
          <h2 className="text-whitewc text-center text-2xl font-bold mb-6">
            INICIAR SESIÓN
          </h2>

          <form onSubmit={handleLogin} className="flex flex-col gap-4">
            {/* Correo */}
            <div className="flex flex-col">
              <label htmlFor="loginEmail" className="text-whitewc mb-1 text-sm">
                Correo Electrónico
              </label>
              <input
                type="email"
                id="loginEmail"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                className="p-2 rounded-md bg-blackbg text-whitewc outline-none"
              />
            </div>

            {/* Contraseña */}
            <div className="flex flex-col">
              <label htmlFor="loginPassword" className="text-whitewc mb-1 text-sm">
                Contraseña
              </label>
              <input
                type="password"
                id="loginPassword"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                className="p-2 rounded-md bg-blackbg text-whitewc outline-none"
              />
            </div>

            {/* Botón */}
            <button
              type="submit"
              className="bg-wisepurple hover:bg-purple-700 text-whitewc font-semibold py-2 rounded-md mt-2"
            >
              INICIAR SESIÓN
            </button>
          </form>

          {/* Mensaje */}
          {message && (
            <p
              className={`mt-4 text-center font-semibold ${
                message.includes("✅") ? "text-green-400" : "text-red-400"
              }`}
            >
              {message}
            </p>
          )}
        </div>
      </div>
    </div>
  );
}