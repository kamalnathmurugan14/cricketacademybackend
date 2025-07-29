import React, { useState } from 'react';
import { TrendingUp, Trophy, Target } from 'lucide-react';

interface CricketerStats {
  runs: number;
  wickets: number;
  matches: number;
}

interface Cricketer {
  id: string;
  name: string;
  photo: string;
  achievements: string[];
  stats: {
    [year: string]: CricketerStats;
  };
}

interface CricketerCardProps {
  cricketer: Cricketer;
}

const CricketerCard: React.FC<CricketerCardProps> = ({ cricketer }) => {
  const [selectedYear, setSelectedYear] = useState('2023');
  const years = Object.keys(cricketer.stats);
  const currentStats = cricketer.stats[selectedYear];

  return (
    <div className="bg-white rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 overflow-hidden group">
      <div className="relative">
        <img
          src={cricketer.photo}
          alt={cricketer.name}
          className="w-full h-48 object-cover group-hover:scale-105 transition-transform duration-300"
        />
        <div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent"></div>
        <div className="absolute bottom-4 left-4 text-white">
          <h3 className="text-xl font-bold">{cricketer.name}</h3>
        </div>
      </div>
      
      <div className="p-6">
        {/* Achievements */}
        <div className="mb-4">
          <div className="flex flex-wrap gap-2">
            {cricketer.achievements.map((achievement, index) => (
              <span
                key={index}
                className="inline-flex items-center px-2 py-1 bg-green-100 text-green-800 text-xs font-medium rounded-full"
              >
                <Trophy className="w-3 h-3 mr-1" />
                {achievement}
              </span>
            ))}
          </div>
        </div>

        {/* Year Selector */}
        <div className="mb-4">
          <div className="flex space-x-2">
            {years.map((year) => (
              <button
                key={year}
                onClick={() => setSelectedYear(year)}
                className={`px-3 py-1 rounded-md text-sm font-medium transition-colors ${
                  selectedYear === year
                    ? 'bg-green-600 text-white'
                    : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                }`}
              >
                {year}
              </button>
            ))}
          </div>
        </div>

        {/* Stats */}
        <div className="grid grid-cols-3 gap-4 mb-4">
          <div className="text-center p-3 bg-blue-50 rounded-lg">
            <div className="flex items-center justify-center mb-1">
              <Target className="w-4 h-4 text-blue-600 mr-1" />
            </div>
            <div className="text-2xl font-bold text-blue-600">{currentStats.runs}</div>
            <div className="text-xs text-gray-600">Runs</div>
          </div>
          
          <div className="text-center p-3 bg-red-50 rounded-lg">
            <div className="flex items-center justify-center mb-1">
              <TrendingUp className="w-4 h-4 text-red-600 mr-1" />
            </div>
            <div className="text-2xl font-bold text-red-600">{currentStats.wickets}</div>
            <div className="text-xs text-gray-600">Wickets</div>
          </div>
          
          <div className="text-center p-3 bg-green-50 rounded-lg">
            <div className="flex items-center justify-center mb-1">
              <Trophy className="w-4 h-4 text-green-600 mr-1" />
            </div>
            <div className="text-2xl font-bold text-green-600">{currentStats.matches}</div>
            <div className="text-xs text-gray-600">Matches</div>
          </div>
        </div>

        {/* Performance Chart (Simplified) */}
        <div className="mb-4">
          <h4 className="text-sm font-medium text-gray-700 mb-2">Performance Trend</h4>
          <div className="flex items-end space-x-2 h-16">
            {years.map((year) => {
              const stats = cricketer.stats[year];
              const runsHeight = (stats.runs / 2000) * 100;
              return (
                <div
                  key={year}
                  className="flex-1 bg-green-200 rounded-t"
                  style={{ height: `${Math.max(runsHeight, 10)}%` }}
                  title={`${year}: ${stats.runs} runs`}
                ></div>
              );
            })}
          </div>
          <div className="flex justify-between text-xs text-gray-500 mt-1">
            {years.map((year) => (
              <span key={year}>{year}</span>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CricketerCard;