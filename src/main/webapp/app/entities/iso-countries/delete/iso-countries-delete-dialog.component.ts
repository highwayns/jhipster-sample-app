import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIsoCountries } from '../iso-countries.model';
import { IsoCountriesService } from '../service/iso-countries.service';

@Component({
  templateUrl: './iso-countries-delete-dialog.component.html',
})
export class IsoCountriesDeleteDialogComponent {
  isoCountries?: IIsoCountries;

  constructor(protected isoCountriesService: IsoCountriesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.isoCountriesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
