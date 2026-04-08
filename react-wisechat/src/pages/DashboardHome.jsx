import { Link, Outlet } from "react-router-dom";
import { useState } from "react";
import { Home, Bot, LifeBuoy, LogOut } from "lucide-react";
import LogoWiseChat from "../assets/LogoWiseChat.png";

export default function DashboardHome() {
  const [menuOpen, setMenuOpen] = useState(false);

  return (
    <div className="min-h-screen flex bg-black text-white font-montserrat">
      {/* SIDEBAR */}
      <aside className="w-64 bg-black border-r border-gray-800 hidden md:flex flex-col">
        <div className="p-6 flex items-center justify-center border-b border-gray-800">
          <img src={LogoWiseChat} alt="WiseChat Logo" className="w-40" />
        </div>
        <nav className="flex-1 p-4">
          <ul className="space-y-4">
            <li>
              <Link
                to="/dashboard"
                className="flex items-center gap-2 px-4 py-2 rounded-lg hover:bg-wisepurple transition"
              >
                <Home size={20} />
                <span>Inicio</span>
              </Link>
            </li>
            <li>
              <Link
                to="/dashboard/wisebot"
                className="flex items-center gap-2 px-4 py-2 rounded-lg hover:bg-wisepurple transition"
              >
                <Bot size={20} />
                <span>WiseBot</span>
              </Link>
            </li>
            <li>
              <Link
                to="/dashboard/support"
                className="flex items-center gap-2 px-4 py-2 rounded-lg hover:bg-wisepurple transition"
              >
                <LifeBuoy size={20} />
                <span>Soporte</span>
              </Link>
            </li>
          </ul>
        </nav>
        <div className="p-4 border-t border-gray-800">
          <button className="flex items-center gap-2 px-4 py-2 rounded-lg hover:bg-red-600 transition w-full text-left">
            <LogOut size={20} />
            <span>Cerrar Sesión</span>
          </button>
        </div>
      </aside>

      {/* CONTENIDO PRINCIPAL */}
      <div className="flex-1 flex flex-col">
        {/* HEADER MOBILE */}
        <header className="md:hidden flex items-center justify-between px-4 py-3 bg-black border-b border-gray-800">
          <img src={LogoWiseChat} alt="WiseChat Logo" className="w-32" />
          <button
            onClick={() => setMenuOpen(!menuOpen)}
            className="p-2 rounded-lg border border-gray-700"
          >
            ☰
          </button>
        </header>

        {/* MENÚ MOBILE */}
        {menuOpen && (
          <nav className="md:hidden bg-black border-b border-gray-800">
            <ul className="flex flex-col p-4 space-y-3">
              <li>
                <Link
                  to="/dashboard"
                  onClick={() => setMenuOpen(false)}
                  className="flex items-center gap-2 px-4 py-2 rounded-lg hover:bg-wisepurple transition"
                >
                  <Home size={20} />
                  <span>Inicio</span>
                </Link>
              </li>
              <li>
                <Link
                  to="/dashboard/wisebot"
                  onClick={() => setMenuOpen(false)}
                  className="flex items-center gap-2 px-4 py-2 rounded-lg hover:bg-wisepurple transition"
                >
                  <Bot size={20} />
                  <span>WiseBot</span>
                </Link>
              </li>
              <li>
                <Link
                  to="/dashboard/support"
                  onClick={() => setMenuOpen(false)}
                  className="flex items-center gap-2 px-4 py-2 rounded-lg hover:bg-wisepurple transition"
                >
                  <LifeBuoy size={20} />
                  <span>Soporte</span>
                </Link>
              </li>
              <li>
                <button className="flex items-center gap-2 px-4 py-2 rounded-lg hover:bg-red-600 transition w-full text-left">
                  <LogOut size={20} />
                  <span>Cerrar Sesión</span>
                </button>
              </li>
            </ul>
          </nav>
        )}

        {/* CONTENIDO */}
        <main className="flex-1 p-6 bg-gray-900 overflow-y-auto">
          <Outlet />
        </main>
      </div>
    </div>
  );
}