import logo from "../../assets/LogoWisechat1.png";
import { useNavigate } from "react-router-dom";

export default function Impulsa() {
  const navigate = useNavigate();

  return (
    <section className="mt-16">
      <div className="flex items-center justify-center gap-4 flex-wrap py-12 px-20">
        <div className="flex-1 basis-[20%] max-w-[800px]">
          <img src={logo} alt="" className="w-full h-auto block" />
        </div>

        <div className="flex-1 basis-[10%] text-white px-[60px]">
          <p className="text-[2.3rem] font-black mb-4">HAZ CRECER TU NEGOCIO</p>
          <p className="text-[1.4rem] mb-[1.2rem] text-[#dddddd] leading-[1.2]">
            Explora nuestras soluciones innovadoras diseñadas para revolucionar tu experiencia e impulsar el éxito de tus ventas.
          </p>
          <a
            onClick={() => navigate("/register")}
            style={{ cursor: "pointer" }}
          >
            <p className="text-[1.4rem] text-[#5b2bbd] font-medium hover:text-[#6E31DE] hover:font-bold hover:underline">
              MULTIPLICAR X10 MIS VENTAS <i aria-hidden="true"></i>
            </p>
          </a>
        </div>
      </div>
    </section>
  );
}