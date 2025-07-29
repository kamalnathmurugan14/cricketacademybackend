import React from 'react';
import { Check } from 'lucide-react';

interface Facility {
  title: string;
  description: string;
  image: string;
  features: string[];
}

interface FacilityCardProps {
  facility: Facility;
}

const FacilityCard: React.FC<FacilityCardProps> = ({ facility }) => {
  return (
    <div className="bg-white rounded-xl shadow-lg overflow-hidden hover:shadow-xl transition-shadow duration-300">
      <div className="relative">
        <img
          src={facility.image}
          alt={facility.title}
          className="w-full h-48 object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent"></div>
      </div>
      
      <div className="p-6">
        <h3 className="text-xl font-bold text-gray-900 mb-2">{facility.title}</h3>
        <p className="text-gray-600 mb-4">{facility.description}</p>
        
        <div className="space-y-2">
          {facility.features.map((feature, index) => (
            <div key={index} className="flex items-center">
              <Check className="w-4 h-4 text-green-600 mr-2" />
              <span className="text-sm text-gray-700">{feature}</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default FacilityCard;