import { BarChart3, LineChart, TrendingUp, Rocket } from "lucide-react";

export default function Servicios() {
  const servicios = [
    { icon: <BarChart3 className="w-12 h-12 text-wisepurple" />, title: "Analiza", desc: "Obtén métricas claras y en tiempo real" },
    { icon: <LineChart className="w-12 h-12 text-wisepurple" />, title: "Diseña", desc: "Crea estrategias a medida para tu negocio" },
    { icon: <TrendingUp className="w-12 h-12 text-wisepurple" />, title: "Innova", desc: "Implementa soluciones innovadoras" },
    { icon: <Rocket className="w-12 h-12 text-wisepurple" />, title: "Ejecuta", desc: "Lleva tu negocio al siguiente nivel 🚀" },
  ];

  return (
    <section className="py-16 bg-black text-white text-center">
      <h2 className="text-3xl font-bold mb-12">LO QUE <span className="text-wisepurple font-extrabold">WISECHAT</span> PUEDE HACER POR TÍ</h2>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-8 max-w-6xl mx-auto px-6">
        {servicios.map((servicio, idx) => (
          <div key={idx} className="bg-zinc-900 p-6 rounded-xl shadow-md hover:scale-105 transition-transform">
            <div className="flex justify-center mb-4">{servicio.icon}</div>
            <h3 className="text-xl font-semibold mb-2">{servicio.title}</h3>
            <p className="text-gray-300 text-sm">{servicio.desc}</p>
          </div>
        ))}
      </div>
    </section>
  );
}