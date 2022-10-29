package ru.sber.report.util;

import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class RowContentEquals implements ContentEquals {

  public static boolean check(Row lhs, Row rhs) {
    if (lhs == null || rhs == null || (lhs.getPhysicalNumberOfCells()
        != rhs.getPhysicalNumberOfCells())) {
      return false;
    }
    Iterator<Cell> lhsIter = lhs.cellIterator();
    Iterator<Cell> rhsIter = rhs.cellIterator();

    while (lhsIter.hasNext()) {
      Cell lhsCell = lhsIter.next();
      Cell rhsCell = rhsIter.next();
      if (!lhsCell.getStringCellValue().equals(rhsCell.getStringCellValue())) {
        return false;
      }
    }
    return true;
  }
}
