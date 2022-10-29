package ru.sber.report.util;

import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class SheetContentEquals implements ContentEquals {
  public static boolean check(Sheet lhs, Sheet rhs) {
    if (lhs == null || rhs == null || (lhs.getPhysicalNumberOfRows()
        != rhs.getPhysicalNumberOfRows())) {
      return false;
    }
    Iterator<Row> lhsIter = lhs.rowIterator();
    Iterator<Row> rhsIter = rhs.rowIterator();

    while (lhsIter.hasNext()) {
      Row lhsRow = lhsIter.next();
      Row rhsRow = rhsIter.next();
      if (!RowContentEquals.check(lhsRow, rhsRow)) {
        return false;
      }
    }
    return true;
  }
}
