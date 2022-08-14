import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IpAccessLogComponent } from './list/ip-access-log.component';
import { IpAccessLogDetailComponent } from './detail/ip-access-log-detail.component';
import { IpAccessLogUpdateComponent } from './update/ip-access-log-update.component';
import { IpAccessLogDeleteDialogComponent } from './delete/ip-access-log-delete-dialog.component';
import { IpAccessLogRoutingModule } from './route/ip-access-log-routing.module';

@NgModule({
  imports: [SharedModule, IpAccessLogRoutingModule],
  declarations: [IpAccessLogComponent, IpAccessLogDetailComponent, IpAccessLogUpdateComponent, IpAccessLogDeleteDialogComponent],
  entryComponents: [IpAccessLogDeleteDialogComponent],
})
export class IpAccessLogModule {}
