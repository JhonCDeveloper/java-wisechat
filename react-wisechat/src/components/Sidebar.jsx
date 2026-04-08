import { NavLink, useNavigate } from "react-router-dom";
import logo from "../assets/LogoWisechat1.png";
import userLogo from "../assets/Logo WiseChat.png";

export default function Sidebar() {
  const navigate = useNavigate();

  return (
    <div className="sidebar">
      <div className="header">
        <div className="brand">
          <i className="fa-solid fa-w"></i>
          <img src={logo} alt="Logo Wisechat" />
        </div>
      </div>

      <div className="menu-container">
        <ul className="menu">
          <li className="menu-item">
            <NavLink to="/dashboard" className="menu-link">
              <i className="fa-solid fa-house"></i>
              <span>Inicio</span>
            </NavLink>
          </li>
          <li className="menu-item">
            <NavLink to="/dashboard/wisebot" className="menu-link">
              <i className="fa-solid fa-robot"></i>
              <span>WiseBot</span>
            </NavLink>
          </li>
          <li className="menu-item">
            <NavLink to="/dashboard/support" className="menu-link">
              <i className="fa-solid fa-question"></i>
              <span>Ayuda</span>
            </NavLink>
          </li>
        </ul>
      </div>

      <div className="footer">
        <div className="user">
          <div className="user-img">
            <img src={userLogo} alt="user" />
          </div>
          <div className="user-data">
            <span className="name">Wisechat</span>
            <span className="email">wwisechat@gmail.com</span>
          </div>
          <button
            onClick={() => navigate("/")}
            className="user-icon bg-transparent border-0 cursor-pointer"
          >
            <i className="fa-solid fa-arrow-right-from-bracket"></i>
          </button>
        </div>
      </div>
    </div>
  );
}