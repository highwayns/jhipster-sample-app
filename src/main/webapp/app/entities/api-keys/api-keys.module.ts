import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ApiKeysComponent } from './list/api-keys.component';
import { ApiKeysDetailComponent } from './detail/api-keys-detail.component';
import { ApiKeysUpdateComponent } from './update/api-keys-update.component';
import { ApiKeysDeleteDialogComponent } from './delete/api-keys-delete-dialog.component';
import { ApiKeysRoutingModule } from './route/api-keys-routing.module';

@NgModule({
  imports: [SharedModule, ApiKeysRoutingModule],
  declarations: [ApiKeysComponent, ApiKeysDetailComponent, ApiKeysUpdateComponent, ApiKeysDeleteDialogComponent],
  entryComponents: [ApiKeysDeleteDialogComponent],
})
export class ApiKeysModule {}
