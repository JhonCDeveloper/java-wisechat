import Banner from "../components/HomeExtras/Banner";
import Lista from "../components/HomeExtras/Lista";
import Impulsa from "../components/HomeExtras/Impulsa";
import Servicios from "../components/HomeExtras/Servicios";
import CTARegistro from "../components/HomeExtras/CTARegistro";
import Testimonios from "../components/HomeExtras/Testimonios";
import Footer from "../components/Footer";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import LogoWiseChat from "../assets/LogoWiseChat.png";


export default function Home() {
    // Estado para el efecto máquina de escribir
    const [isTyping, setIsTyping] = useState(true);
    const [displayedText, setDisplayedText] = useState("");
    const fullText =
        "Nuestra IA está calentando motores para ayudarte a vender más 🚀";

    // Estado para el menú móvil
    const [menuOpen, setMenuOpen] = useState(false);

    // Efecto máquina de escribir
    useEffect(() => {
        let i = 0;
        function typeWriter() {
            if (i < fullText.length) {
                setDisplayedText((prev) => prev + fullText.charAt(i));
                i++;
                setTimeout(typeWriter, Math.random() * 100 + 50);
            } else {
                setIsTyping(false);
            }
        }
        const timer = setTimeout(typeWriter, 1000);
        return () => clearTimeout(timer);
    }, []);

    return (
        <div className="min-h-screen bg-black font-montserrat text-white flex flex-col">
            {/* HEADER */}
            <header className="flex items-center justify-between px-6 py-4 bg-black relative">
                <img
                    src={LogoWiseChat}
                    alt="Wisechat Logo"
                    className="w-52 max-w-[40%] h-auto"
                />

                {/* Botón Hamburguesa */}
                <div
                    className="md:hidden flex flex-col gap-1 cursor-pointer z-50"
                    onClick={() => setMenuOpen(!menuOpen)}
                >
                    <span className="block w-8 h-0.5 bg-white"></span>
                    <span className="block w-8 h-0.5 bg-white"></span>
                    <span className="block w-8 h-0.5 bg-white"></span>
                </div>

                {/* Menú Desktop */}
                <ul className="hidden md:flex gap-6 font-bold">
                    <li>
                        <Link
                            to="/login"
                            className="bg-wisepurple px-6 py-2 rounded-lg hover:bg-white hover:text-black transition"
                        >
                            INICIAR SESIÓN
                        </Link>
                    </li>
                    <li>
                        <Link
                            to="/register"
                            className="bg-wisepurple px-6 py-2 rounded-lg hover:bg-white hover:text-black transition"
                        >
                            REGISTRARME
                        </Link>
                    </li>
                </ul>

                {/* Menú Móvil */}
                {menuOpen && (
                    <ul className="absolute top-full left-0 w-full bg-black flex flex-row justify-center gap-4 p-4 font-bold md:hidden">
                        <li>
                            <Link
                                to="/login"
                                className="bg-wisepurple px-4 py-2 rounded-lg text-center hover:bg-white hover:text-black transition"
                                onClick={() => setMenuOpen(false)}
                            >
                                INICIAR SESIÓN
                            </Link>
                        </li>
                        <li>
                            <Link
                                to="/register"
                                className="bg-wisepurple px-4 py-2 rounded-lg text-center hover:bg-white hover:text-black transition"
                                onClick={() => setMenuOpen(false)}
                            >
                                REGISTRARME
                            </Link>
                        </li>
                    </ul>
                )}
            </header>

            {/* MAIN */}
            <main className="flex-1 flex flex-col md:flex-row">
                {/* HERO */}
                <section className="flex-1 min-h-[85vh] flex flex-col justify-center text-left px-8 md:max-w-[50%]">
                    <h1 className="font-extrabold text-white leading-tight 
               text-[36px] sm:text-[48px] md:text-[64px]">
                        AUMENTA LAS VENTAS DE TU NEGOCIO{" "}
                        <span className="block text-wisepurple text-6xl md:text-8xl">
                            GRATIS
                        </span>
                    </h1>
                    <p className="mt-4 text-lg">
                        TRANSFORMA TU NEGOCIO Y{" "}
                        <span className="text-wisepurple font-bold">DISPARA TUS VENTAS</span>{" "}
                        CON NUESTRO{" "}
                        <span className="text-white font-bold">SOFTWARE REVOLUCIONARIO</span>
                    </p>
                    <Link
                        to="/login"
                        className="mt-6 w-80 inline-block bg-wisepurple text-white px-6 py-3 rounded-lg text-xl font-extrabold hover:bg-white hover:text-black transition"
                    >
                        POTENCIAR MIS VENTAS
                    </Link>
                </section>

                {/* LISTENER */}
                <section className="flex-1 flex justify-center items-center bg-black px-4 w-full overflow-x-hidden">
                    <div className="flex flex-col items-center max-w-full">
                        {/* Loader móvil: 20 barras */}
                        <div className="flex md:hidden items-end h-10 sm:h-12 gap-px sm:gap-0.5 overflow-hidden">
                            {Array.from({ length: 20 }).map((_, i) => (
                                <span
                                    key={`m-${i}`}
                                    aria-hidden="true"
                                    className="block bg-wisepurple w-[2px] sm:w-[3px] animate-[wave_1.2s_ease-in-out_infinite]"
                                    style={{ animationDelay: `${i * 0.06}s` }}
                                />
                            ))}
                        </div>

                        {/* Loader desktop: 40 barras */}
                        <div className="hidden md:flex items-end h-12 lg:h-16 gap-0.5 overflow-hidden">
                            {Array.from({ length: 40 }).map((_, i) => (
                                <span
                                    key={`d-${i}`}
                                    aria-hidden="true"
                                    className="block bg-wisepurple w-[3px] lg:w-[6px] animate-[wave_1.2s_ease-in-out_infinite]"
                                    style={{ animationDelay: `${i * 0.05}s` }}
                                />
                            ))}
                        </div>

                        {/* Texto con efecto máquina de escribir (responsivo y sin overflow) */}
                        <p className={`mt-4 text-base sm:text-lg md:text-xl font-mono overflow-hidden text-center break-words max-w-[90vw] ${isTyping ? "border-r-2 border-white" : ""
                            }`}>
                            {displayedText}
                        </p>
                    </div>
                </section>
            </main>

            {/* EXTRA SECTIONS */}
            <Banner />
            <Lista />
            <Impulsa />
            <Servicios />
            <CTARegistro />
            <Testimonios />
            <Footer />
        </div>
    );
}