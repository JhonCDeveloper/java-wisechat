import ilustracion from "../../assets/que_esperas.png";
import { useNavigate } from "react-router-dom";

export default function CTARegistro() {
   const navigate = useNavigate();

  return (
    <section className="mt-0">
      <p className="text-center text-[2.2rem] mt-12 font-extrabold pb-[10px] text-white">
        QUÉ ESPERAS PARA UNIRTE
      </p>

      <div className="mt-4 flex items-center justify-center gap-4 flex-wrap p-8 bg-[#4C1D95] rounded-[50px] mx-auto max-w-[1200px] w-[90%] box-border">
        <div className="flex-1 basis-[300px] text-white p-4 box-border">
          <p className="text-[2rem] font-extrabold text-white">
            Planes flexibles que se adapten a tus necesidades
          </p>
          <p className="text-[1rem] text-[#dddddd] font-semibold py-4">
            Consulte los precios personalizados diseñados en función de sus necesidades únicas, asegurándose de obtener el mejor valor.
          </p>
          <a onClick={() => navigate("/register")}
            className="no-underline bg-[#6E31DE] text-white rounded-[10px] px-[15px] py-[10px] font-extrabold inline-block transition-transform hover:-translate-y-[10px]"
            href=""
          >
            REGISTRARME
          </a>
        </div>

        <div className="flex-1 basis-[300px] max-w-[500px]">
          <img src={ilustracion} alt="" className="w-full h-auto block" />
        </div>
      </div>
    </section>
  );
}