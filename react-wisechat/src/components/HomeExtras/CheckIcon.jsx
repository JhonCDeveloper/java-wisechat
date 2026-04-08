import { Check } from "lucide-react";

export default function CheckIcon() {
  return (
    <div className="flex items-center justify-center h-screen bg-black">
      <div className="bg-purple-600 p-4 rounded-full">
        <Check className="text-white  w-6 h-6" />
      </div>
    </div>
  );
}