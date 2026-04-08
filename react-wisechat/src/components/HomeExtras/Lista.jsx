export default function Lista() {
  return (
    <div className="mt-20">
      <ul className="list-none flex flex-wrap justify-center items-center gap-4">
        <li className="px-[25px] py-[10px] text-[1.3rem] bg-white text-[#09090B] font-semibold rounded-[20px] transition-transform hover:-translate-y-[5px] hover:bg-[#6E31DE] hover:text-white">
          Innova
        </li>
        <li className="px-[25px] py-[10px] text-[1.3rem] bg-[#6E31DE] text-white font-semibold rounded-[20px] transition-transform hover:-translate-y-[5px] hover:bg-white hover:text-[#09090B]">
          Analiza
        </li>
        <li className="px-[25px] py-[10px] text-[1.3rem] bg-white text-[#09090B] font-semibold rounded-[20px] transition-transform hover:-translate-y-[5px] hover:bg-[#6E31DE] hover:text-white">
          Diseña
        </li>
        <li className="px-[25px] py-[10px] text-[1.3rem] bg-[#6E31DE] text-white font-semibold rounded-[20px] transition-transform hover:-translate-y-[5px] hover:bg-white hover:text-[#09090B]">
          Ejecuta
        </li>
      </ul>
    </div>
  );
}