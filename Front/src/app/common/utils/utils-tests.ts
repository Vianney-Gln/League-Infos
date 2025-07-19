import { DebugElement } from '@angular/core';

export function getByDataTestAttr(element: DebugElement, attrValue: string): HTMLElement | null {
  return element.nativeElement.querySelector(`[data-test="${attrValue}"]`);
}

export function clickButtonByDataTestAttr(element: DebugElement, attrValue: string): void {
  const button: HTMLElement | null = element.nativeElement.querySelector(`[data-test="${attrValue}"]`);
  if (button) {
    button.click();
  }
}

export function getAllByDataTestAttr(element: DebugElement, attrValue: string): HTMLElement[] | null {
  return element.nativeElement.querySelectorAll(`[data-test="${attrValue}"]`);
}
