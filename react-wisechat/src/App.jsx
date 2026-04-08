import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import DashboardHome from "./pages/DashboardHome";
import Form from "./pages/Form";
import Support from "./pages/Support";
import WiseBot from "./pages/WiseBot";

export default function App() {
  console.log("✅ App se está renderizando");

  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/form" element={<Form />} />
      <Route path="/dashboard" element={<DashboardHome />} />

      {/* Rutas Hijas del Dashboard */}
      <Route path="/dashboard" element={<DashboardHome />}>
        <Route index element={<div>Bienvenido al Dashboard</div>} /> {/* Página inicial */}
        <Route path="wisebot" element={<WiseBot />} />
        <Route path="support" element={<Support />} />
      </Route>
      
    </Routes>
  );
}