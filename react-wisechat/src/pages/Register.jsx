import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from "../assets/LogoWiseChat.png";

export default function Register() {
  const navigate = useNavigate();

  // Estados
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [message, setMessage] = useState("");

  // Asegurar que existan usuarios en localStorage
  useEffect(() => {
    const storedUsers = JSON.parse(localStorage.getItem("users"));
    if (!storedUsers) {
      localStorage.setItem("users", JSON.stringify([]));
    }
  }, []);

  const isEmailRegistered = (email) => {
    const users = JSON.parse(localStorage.getItem("users")) || [];
    return users.some((user) => user.email === email);
  };

  const handleRegister = (e) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      setMessage("❌ Las contraseñas no coinciden");
      return;
    }

    if (isEmailRegistered(email.trim())) {
      setMessage("❌ Este correo ya está registrado");
      return;
    }

    const users = JSON.parse(localStorage.getItem("users")) || [];
    users.push({ email: email.trim(), password: password.trim() });
    localStorage.setItem("users", JSON.stringify(users));

    setMessage("✅ Registro exitoso, redirigiendo...");
    setTimeout(() => navigate("/login"), 1500);
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
        {/* Botón Login */}
        <div className="mb-6">
          <Link
            to="/login"
            className="bg-wisepurple hover:bg-purple-700 text-whitewc text-lg font-semibold px-6 py-2 rounded-lg"
          >
            INICIAR SESIÓN
          </Link>
        </div>

        {/* Caja de Registro */}
        <div className="bg-[#1C1C1E] rounded-lg shadow-lg p-6 w-full max-w-sm">
          <h2 className="text-whitewc text-center text-2xl font-bold mb-6">
            REGISTRARME
          </h2>

          <form onSubmit={handleRegister} className="flex flex-col gap-4">
            {/* Email */}
            <div className="flex flex-col">
              <label htmlFor="registerEmail" className="text-whitewc mb-1 text-sm">
                Correo Electrónico
              </label>
              <input
                type="email"
                id="registerEmail"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                className="p-2 rounded-md bg-blackbg text-whitewc outline-none"
              />
            </div>

            {/* Contraseña */}
            <div className="flex flex-col relative">
              <label htmlFor="registerPassword" className="text-whitewc mb-1 text-sm">
                Contraseña
              </label>
              <input
                type={showPassword ? "text" : "password"}
                id="registerPassword"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                className="p-2 rounded-md bg-blackbg text-whitewc outline-none pr-10"
              />
              <span
                onClick={() => setShowPassword(!showPassword)}
                className="absolute right-3 top-9 cursor-pointer material-symbols-outlined text-whitewc"
              >
                {showPassword ? "visibility_off" : "visibility"}
              </span>
            </div>

            {/* Confirmar Contraseña */}
            <div className="flex flex-col relative">
              <label htmlFor="confirmPassword" className="text-whitewc mb-1 text-sm">
                Confirmar Contraseña
              </label>
              <input
                type={showConfirmPassword ? "text" : "password"}
                id="confirmPassword"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
                className="p-2 rounded-md bg-blackbg text-whitewc outline-none pr-10"
              />
              <span
                onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                className="absolute right-3 top-9 cursor-pointer material-symbols-outlined text-whitewc"
              >
                {showConfirmPassword ? "visibility_off" : "visibility"}
              </span>
            </div>

            {/* Mensaje si no coinciden */}
            {confirmPassword && password !== confirmPassword && (
              <small className="text-red-400 font-medium">
                Las contraseñas no coinciden
              </small>
            )}

            {/* Botón */}
            <button
              type="submit"
              className="bg-wisepurple hover:bg-purple-700 text-whitewc font-semibold py-2 rounded-md mt-2"
            >
              REGISTRARME
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