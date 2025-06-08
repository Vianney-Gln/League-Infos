import { DebugElement } from '@angular/core';

export function getByDataTestAttr(element: DebugElement, attrValue: string): HTMLElement | null {
  return element.nativeElement.querySelector(`[data-test="${attrValue}"]`);
}
