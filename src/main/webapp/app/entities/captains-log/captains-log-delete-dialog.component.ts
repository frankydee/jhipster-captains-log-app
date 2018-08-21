import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICaptainsLog } from 'app/shared/model/captains-log.model';
import { CaptainsLogService } from './captains-log.service';

@Component({
    selector: 'jhi-captains-log-delete-dialog',
    templateUrl: './captains-log-delete-dialog.component.html'
})
export class CaptainsLogDeleteDialogComponent {
    captainsLog: ICaptainsLog;

    constructor(
        private captainsLogService: CaptainsLogService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.captainsLogService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'captainsLogListModification',
                content: 'Deleted an captainsLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-captains-log-delete-popup',
    template: ''
})
export class CaptainsLogDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ captainsLog }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CaptainsLogDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.captainsLog = captainsLog;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
