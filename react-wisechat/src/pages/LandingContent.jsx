import Header from '../components/Header';
import CheckIcon from '../components/CheckIcon';
export default function LandingContent() {
  return (
    <>
      < Header />
      <main className=' px-12 '>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 h-screen">
          {/* Columna izquierda */}
          <div className="grid grid-rows-[2rem_20rem_0.3fr] gap-4">
            <div className=" text-white font-extralight font-inter text-left">PRUEBA GRATUITA DE 15 DÍAS</div>
            <div className="flex  justify-center">
              <h1 className="font-montserrat leading-none text-left text-whitewc font-[800] text-[4rem]">
              AUMENTA LAS VENTAS DE TU NEGOCIO{" "}
              <span className="text-wisepurple text-[6.25rem]">GRATIS</span>
            </h1>
            </div>
            <div className="bg-blue-400">Fila 3 (4rem)</div>
          </div>

          {/* Columna derecha */}
          <div className="bg-purple-400">Columna derecha (full height)</div>
        </div>
      </main>
    </>
  );
}