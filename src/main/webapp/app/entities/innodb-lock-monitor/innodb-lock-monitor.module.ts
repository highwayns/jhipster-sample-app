import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InnodbLockMonitorComponent } from './list/innodb-lock-monitor.component';
import { InnodbLockMonitorDetailComponent } from './detail/innodb-lock-monitor-detail.component';
import { InnodbLockMonitorUpdateComponent } from './update/innodb-lock-monitor-update.component';
import { InnodbLockMonitorDeleteDialogComponent } from './delete/innodb-lock-monitor-delete-dialog.component';
import { InnodbLockMonitorRoutingModule } from './route/innodb-lock-monitor-routing.module';

@NgModule({
  imports: [SharedModule, InnodbLockMonitorRoutingModule],
  declarations: [
    InnodbLockMonitorComponent,
    InnodbLockMonitorDetailComponent,
    InnodbLockMonitorUpdateComponent,
    InnodbLockMonitorDeleteDialogComponent,
  ],
  entryComponents: [InnodbLockMonitorDeleteDialogComponent],
})
export class InnodbLockMonitorModule {}
