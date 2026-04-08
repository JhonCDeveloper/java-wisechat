import { useState } from "react";
import logo from '../assets/LogoWiseChat.png'; 

 function Header() {

  const [menuOpen, setMenuOpen] = useState(false);

  return (
    <header className="bg-blackbg text-white shadow-md sticky top-0 z-50">
      <div className="max-w-full mx-auto m-5 px-12 flex justify-between items-center h-20">
        
        {/* Logo */}
        <img src={logo} alt="Logo WiseChat" className="box-border h-auto w-64" />

        {/* Botón Login Escritorio */}
        <div className='hidden md:flex gap-4'>
           <button className="bg-wisepurple font-montserrat font-bold hover:bg-whitewc hover:text-blackbg text-whitewc px-10 py-3 rounded-lg">
            INICIAR SESIÓN
          </button>
          <button className="bg-wisepurple font-montserrat font-bold hover:bg-whitewc hover:text-blackbg text-whitewc px-10 py-3 rounded-lg">
            REGISTRARME
          </button>
        </div>

          {/* Botón Hamburger Menu */}

          <button className='md:hidden flex flex-col gap-1 focus:outline-none cursor-pointer' onClick={() => setMenuOpen(!menuOpen)}>

            <span className="block w-6 h-0.5 bg-whitewc"></span>
            <span className="block w-6 h-0.5 bg-whitewc"></span>
            <span className="block w-6 h-0.5 bg-whitewc"></span>

          </button>

        </div>

        {/* Menú Móvil */}

        {menuOpen && (
        <div className="md:hidden bg-blackbg border-t border-gray-700 px-4 py-4 flex flex-col gap-4">
          <button className="bg-wisepurple font-montserrat font-bold hover:bg-whitewc hover:text-blackbg text-whitewc px-10 py-3 rounded-lg">
            INICIAR SESIÓN
          </button>
          <button className="bg-wisepurple font-montserrat font-bold hover:bg-whitewc hover:text-blackbg text-whitewc px-10 py-3 rounded-lg">
            REGISTRARME
          </button>
        </div>
      )}
    </header>
  );
}

export default Header;