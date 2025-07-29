import React, { useState } from 'react';
import { Calendar, Clock, MapPin, CreditCard, Users, CheckCircle } from 'lucide-react';

const Bookings: React.FC = () => {
  const [bookingType, setBookingType] = useState<'ground' | 'net'>('ground');
  const [selectedDate, setSelectedDate] = useState('');
  const [selectedTimeSlot, setSelectedTimeSlot] = useState('');
  const [selectedGround, setSelectedGround] = useState('');

  const grounds = [
    {
      id: '1',
      name: 'Main Ground A',
      type: 'Match Ground',
      capacity: 22,
      pricePerHour: 500,
      facilities: ['Floodlights', 'Pavilion', 'Scoreboard']
    },
    {
      id: '2',
      name: 'Practice Ground B',
      type: 'Practice Ground',
      capacity: 15,
      pricePerHour: 350,
      facilities: ['Floodlights', 'Equipment Storage']
    },
    {
      id: '3',
      name: 'Youth Ground C',
      type: 'Youth Ground',
      capacity: 12,
      pricePerHour: 300,
      facilities: ['Smaller boundaries', 'Safety features']
    }
  ];

  const nets = [
    {
      id: '1',
      name: 'Net 1-3',
      description: 'Premium practice nets with bowling machine',
      pricePerHour: 150,
      features: ['Bowling Machine', 'Video Analysis']
    },
    {
      id: '2',
      name: 'Net 4-6',
      description: 'Standard practice nets',
      pricePerHour: 100,
      features: ['Standard Setup', 'Equipment Available']
    },
    {
      id: '3',
      name: 'Net 7-10',
      description: 'Basic practice nets',
      pricePerHour: 80,
      features: ['Basic Setup']
    }
  ];

  const timeSlots = [
    '6:00 AM - 7:00 AM',
    '7:00 AM - 8:00 AM',
    '8:00 AM - 9:00 AM',
    '9:00 AM - 10:00 AM',
    '10:00 AM - 11:00 AM',
    '11:00 AM - 12:00 PM',
    '12:00 PM - 1:00 PM',
    '1:00 PM - 2:00 PM',
    '2:00 PM - 3:00 PM',
    '3:00 PM - 4:00 PM',
    '4:00 PM - 5:00 PM',
    '5:00 PM - 6:00 PM',
    '6:00 PM - 7:00 PM',
    '7:00 PM - 8:00 PM',
    '8:00 PM - 9:00 PM'
  ];

  const availableSlots = [
    '8:00 AM - 9:00 AM',
    '10:00 AM - 11:00 AM',
    '2:00 PM - 3:00 PM',
    '4:00 PM - 5:00 PM',
    '6:00 PM - 7:00 PM'
  ];

  const handleBooking = () => {
    // Mock booking process - would integrate with payment gateway
    alert('Redirecting to payment gateway...');
  };

  const getSelectedFacility = () => {
    if (bookingType === 'ground') {
      return grounds.find(g => g.id === selectedGround);
    } else {
      return nets.find(n => n.id === selectedGround);
    }
  };

  const selectedFacility = getSelectedFacility();

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-4">Book Your Practice Session</h1>
          <p className="text-lg text-gray-600">Reserve grounds and nets for your cricket training</p>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Booking Form */}
          <div className="lg:col-span-2 space-y-6">
            {/* Booking Type Selection */}
            <div className="bg-white rounded-lg shadow p-6">
              <h2 className="text-xl font-semibold text-gray-900 mb-4">Select Booking Type</h2>
              <div className="grid grid-cols-2 gap-4">
                <button
                  onClick={() => setBookingType('ground')}
                  className={`p-4 rounded-lg border-2 transition-all ${
                    bookingType === 'ground'
                      ? 'border-green-600 bg-green-50 text-green-900'
                      : 'border-gray-200 hover:border-green-300'
                  }`}
                >
                  <MapPin className="h-6 w-6 mx-auto mb-2" />
                  <div className="font-semibold">Ground Booking</div>
                  <div className="text-sm text-gray-600">Full cricket grounds</div>
                </button>
                
                <button
                  onClick={() => setBookingType('net')}
                  className={`p-4 rounded-lg border-2 transition-all ${
                    bookingType === 'net'
                      ? 'border-green-600 bg-green-50 text-green-900'
                      : 'border-gray-200 hover:border-green-300'
                  }`}
                >
                  <Users className="h-6 w-6 mx-auto mb-2" />
                  <div className="font-semibold">Net Practice</div>
                  <div className="text-sm text-gray-600">Practice nets</div>
                </button>
              </div>
            </div>

            {/* Facility Selection */}
            <div className="bg-white rounded-lg shadow p-6">
              <h2 className="text-xl font-semibold text-gray-900 mb-4">
                Choose {bookingType === 'ground' ? 'Ground' : 'Net'}
              </h2>
              <div className="grid grid-cols-1 gap-4">
                {(bookingType === 'ground' ? grounds : nets).map((facility) => (
                  <div
                    key={facility.id}
                    className={`p-4 rounded-lg border-2 cursor-pointer transition-all ${
                      selectedGround === facility.id
                        ? 'border-green-600 bg-green-50'
                        : 'border-gray-200 hover:border-green-300'
                    }`}
                    onClick={() => setSelectedGround(facility.id)}
                  >
                    <div className="flex justify-between items-start mb-2">
                      <div>
                        <h3 className="font-semibold text-gray-900">{facility.name}</h3>
                        <p className="text-sm text-gray-600">
                          {bookingType === 'ground' ? (facility as any).type : (facility as any).description}
                        </p>
                      </div>
                      <div className="text-right">
                        <div className="text-lg font-bold text-green-600">
                          ₹{(facility as any).pricePerHour}/hour
                        </div>
                        {bookingType === 'ground' && (
                          <div className="text-sm text-gray-500">
                            Up to {(facility as any).capacity} players
                          </div>
                        )}
                      </div>
                    </div>
                    
                    <div className="flex flex-wrap gap-2 mt-3">
                      {(bookingType === 'ground' ? (facility as any).facilities : (facility as any).features).map((feature: string, index: number) => (
                        <span
                          key={index}
                          className="px-2 py-1 bg-gray-100 text-gray-700 text-xs rounded-full"
                        >
                          {feature}
                        </span>
                      ))}
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* Date Selection */}
            <div className="bg-white rounded-lg shadow p-6">
              <h2 className="text-xl font-semibold text-gray-900 mb-4 flex items-center">
                <Calendar className="h-5 w-5 mr-2 text-green-600" />
                Select Date
              </h2>
              <input
                type="date"
                value={selectedDate}
                onChange={(e) => setSelectedDate(e.target.value)}
                min={new Date().toISOString().split('T')[0]}
                className="w-full p-3 border border-gray-300 rounded-md focus:ring-green-500 focus:border-green-500"
              />
            </div>

            {/* Time Slot Selection */}
            {selectedDate && (
              <div className="bg-white rounded-lg shadow p-6">
                <h2 className="text-xl font-semibold text-gray-900 mb-4 flex items-center">
                  <Clock className="h-5 w-5 mr-2 text-green-600" />
                  Select Time Slot
                </h2>
                <div className="grid grid-cols-2 md:grid-cols-3 gap-3">
                  {timeSlots.map((slot) => {
                    const isAvailable = availableSlots.includes(slot);
                    return (
                      <button
                        key={slot}
                        onClick={() => isAvailable && setSelectedTimeSlot(slot)}
                        disabled={!isAvailable}
                        className={`p-3 rounded-lg border text-sm font-medium transition-all ${
                          selectedTimeSlot === slot
                            ? 'border-green-600 bg-green-600 text-white'
                            : isAvailable
                            ? 'border-gray-200 hover:border-green-300 hover:bg-green-50'
                            : 'border-gray-200 bg-gray-100 text-gray-400 cursor-not-allowed'
                        }`}
                      >
                        {slot}
                        {!isAvailable && (
                          <div className="text-xs mt-1">Booked</div>
                        )}
                      </button>
                    );
                  })}
                </div>
              </div>
            )}
          </div>

          {/* Booking Summary */}
          <div className="space-y-6">
            <div className="bg-white rounded-lg shadow p-6 sticky top-8">
              <h2 className="text-xl font-semibold text-gray-900 mb-4">Booking Summary</h2>
              
              {selectedFacility ? (
                <div className="space-y-4">
                  <div className="flex justify-between items-center pb-2 border-b">
                    <span className="text-gray-600">Facility:</span>
                    <span className="font-medium">{selectedFacility.name}</span>
                  </div>
                  
                  {selectedDate && (
                    <div className="flex justify-between items-center pb-2 border-b">
                      <span className="text-gray-600">Date:</span>
                      <span className="font-medium">{new Date(selectedDate).toLocaleDateString()}</span>
                    </div>
                  )}
                  
                  {selectedTimeSlot && (
                    <div className="flex justify-between items-center pb-2 border-b">
                      <span className="text-gray-600">Time:</span>
                      <span className="font-medium">{selectedTimeSlot}</span>
                    </div>
                  )}
                  
                  <div className="flex justify-between items-center pb-2 border-b">
                    <span className="text-gray-600">Price per hour:</span>
                    <span className="font-medium">₹{(selectedFacility as any).pricePerHour}</span>
                  </div>
                  
                  <div className="flex justify-between items-center pb-2 border-b">
                    <span className="text-gray-600">Duration:</span>
                    <span className="font-medium">1 hour</span>
                  </div>
                  
                  <div className="flex justify-between items-center text-lg font-bold">
                    <span>Total Amount:</span>
                    <span className="text-green-600">₹{(selectedFacility as any).pricePerHour}</span>
                  </div>
                  
                  {selectedDate && selectedTimeSlot && (
                    <button
                      onClick={handleBooking}
                      className="w-full mt-6 bg-green-600 text-white py-3 px-4 rounded-md hover:bg-green-700 transition-colors flex items-center justify-center"
                    >
                      <CreditCard className="h-5 w-5 mr-2" />
                      Proceed to Payment
                    </button>
                  )}
                </div>
              ) : (
                <p className="text-gray-500 text-center">Select a facility to see booking summary</p>
              )}
            </div>

            {/* Recent Bookings */}
            <div className="bg-white rounded-lg shadow p-6">
              <h3 className="text-lg font-semibold text-gray-900 mb-4">Recent Bookings</h3>
              <div className="space-y-3">
                <div className="flex items-center justify-between p-3 bg-green-50 rounded-lg">
                  <div>
                    <div className="font-medium text-green-900">Main Ground A</div>
                    <div className="text-sm text-green-700">Jan 20, 10:00 AM - 12:00 PM</div>
                  </div>
                  <CheckCircle className="h-5 w-5 text-green-600" />
                </div>
                
                <div className="flex items-center justify-between p-3 bg-blue-50 rounded-lg">
                  <div>
                    <div className="font-medium text-blue-900">Net 1-3</div>
                    <div className="text-sm text-blue-700">Jan 18, 6:00 PM - 7:00 PM</div>
                  </div>
                  <CheckCircle className="h-5 w-5 text-blue-600" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Bookings;