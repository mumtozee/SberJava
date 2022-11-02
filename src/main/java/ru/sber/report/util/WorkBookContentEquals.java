package ru.sber.report.util;

import java.util.Iterator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class WorkBookContentEquals implements ContentEquals {
  public static boolean check(Workbook lhs, Workbook rhs) {
    if (lhs == null || rhs == null || (lhs.getNumberOfSheets()
        != rhs.getNumberOfSheets())) {
      return false;
    }
    Iterator<Sheet> lhsIter = lhs.sheetIterator();
    Iterator<Sheet> rhsIter = rhs.sheetIterator();

    while (lhsIter.hasNext()) {
      Sheet lhsSheet = lhsIter.next();
      Sheet rhsSheet = rhsIter.next();
      if (!SheetContentEquals.check(lhsSheet, rhsSheet)) {
        return false;
      }
    }
    return true;
  }
}
