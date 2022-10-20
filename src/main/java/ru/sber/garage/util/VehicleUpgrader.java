package ru.sber.garage.util;

import ru.sber.garage.Vehicle;

public interface VehicleUpgrader<OldT extends Vehicle, NewT extends Vehicle> {
   NewT upgrade(OldT car);
}
