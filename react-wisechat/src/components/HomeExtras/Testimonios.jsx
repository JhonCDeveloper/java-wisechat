import fondo from "../../assets/pexels-fauxels-3184299.png";
import carlos from "../../assets/Carlos.jpg";
import laura from "../../assets/Laura.png";

export default function Testimonios() {
  return (
    <section className="mt-20">
      <div
        className="relative w-full py-12 px-8 text-white overflow-hidden"
        style={{ backgroundImage: `url(${fondo})`, backgroundSize: "cover", backgroundPosition: "center", backgroundRepeat: "no-repeat" }}
      >
        {/* Capa violeta sobre imagen de fondo */}
        <div className="absolute inset-0 bg-[rgba(110,49,222,0.6)]"></div>

        {/* Contenido */}
        <div className="relative z-[2]">
          <p className="text-center text-[1.8rem] font-bold mb-8">
            Ayudando a nuestros usuarios a <br /> vender mejor cada día.
          </p>

          <div className="flex justify-center flex-wrap gap-8">
            <div className="rounded-[20px] p-6 flex items-center gap-4 w-[450px] text-white bg-[#4C1D95]">
              <div className="flex-1">
                <p className="italic mb-2 text-[0.8rem] font-medium">
                  Antes teníamos que pasar horas buscando referencias, redactando anuncios o corrigiendo textos para redes. Ahora, con WiseChat, optimizamos cada tarea. Lo usamos desde la planificación de campañas hasta la mejora de nuestros mensajes publicitarios. ¡Es una herramienta imprescindible para cualquier equipo de publicidad!
                </p>
                <p className="font-bold text-[0.9rem]">Carlos Méndez — Coordinador de Contenido en Publicidad</p>
              </div>
              <div className="shrink-0">
                <img
                  src={carlos}
                  alt="Carlos Méndez — Coordinador de Contenido en Publicidad"
                  className="w-[150px] h-[150px] rounded-full object-cover md:w-[150px] md:h-[150px]"
                />
              </div>
            </div>

            <div className="rounded-[20px] p-6 flex items-center gap-4 w-[450px] text-white bg-[#4C1D95]">
              <div className="flex-1">
                <p className="italic mb-2 text-[0.8rem] font-medium">
                  WiseChat se ha convertido en nuestro mejor aliado en el equipo de marketing. Nos permite generar ideas, redactar copys, estructurar campañas y hasta organizar calendarios de contenido en cuestión de minutos. Es como tener un asistente creativo 24/7 que entiende exactamente lo que necesitamos.
                </p>
                <p className="font-bold text-[0.9rem]">Laura Vargas — Directora Creativa </p>
              </div>
              <div className="shrink-0">
                <img
                  src={laura}
                  alt="Laura Vargas — Directora Creativa"
                  className="w-[150px] h-[150px] rounded-full object-cover md:w-[150px] md:h-[150px]"
                />
              </div>
            </div>
          </div>

          <div className="text-center mt-8">
            <a
              href=""
              className="text-white bg-transparent px-[25px] py-[10px] no-underline font-bold inline-block border-2 border-white rounded-[10px] transition-transform hover:-translate-y-[5px]"
            >
              Testimonios de Nuestros Usuarios
            </a>
          </div>
        </div>
      </div>
    </section>
  );
}